#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <assert.h>
#include <stdint.h>
#include <pthread.h>
#include "crawler.h"
#include "mythreads.h"

// initialize queues to empty linked list
node *links = NULL;
node *pages = NULL;

// hash set for links
node **table;

// counter for when to stop
volatile int counter = 0;

// bounded link queue
volatile int max_link = 0;

volatile int page_count = 0;
volatile int link_count = 0;

// links: 2 cv, 1 mutex
pthread_cond_t l_empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t l_fill = PTHREAD_COND_INITIALIZER;
pthread_mutex_t l_m = PTHREAD_MUTEX_INITIALIZER;

// pages: 1 cv, 1 mutex
pthread_cond_t p_fill = PTHREAD_COND_INITIALIZER;
pthread_mutex_t p_m = PTHREAD_MUTEX_INITIALIZER;

// exiting from main()
pthread_cond_t done_cv = PTHREAD_COND_INITIALIZER;
pthread_mutex_t done_m = PTHREAD_MUTEX_INITIALIZER;
int done = 0;

// callbacks
char * (*fetch_fn)(char *url);
void (*edge_fn)(char *from, char *to);

void do_tokenizing(char *orig) {
  printf("***tokenizing...\n------------------------\n%s\n----------------------\n", orig);
  char *str = strdup(orig);

  char *curr, *new_str, *word;
  char *saveptr1, *saveptr2;


  while( (curr = strstr(str, "link:")) != NULL) {
    new_str = strdup(curr+1);

    curr = strtok_r(curr, " \n", &saveptr1);
    assert(curr != NULL);

    word = strtok_r(curr, ":", &saveptr2);
    word = strtok_r(NULL, ":", &saveptr2);

    printf("found word: %s\n", word);
    assert(word != NULL);

    // check hash set and, if not present, add
    // link producer
    Mutex_lock(&l_m);
    while(link_count == max_link) {
      // wait
      Cond_wait(&l_empty, &l_m);
    }
    
    if(!contains(table, word)) {
      // do pushing
      links = push(strdup(word), links);
      link_count++;
      // update table
      add(table, word);
    }
    
    Cond_signal(&l_fill);
    Mutex_unlock(&l_m);

    free(str);
    str = new_str;
  }

  free(str);

  return;
}

void parse(node *n) {

  // do some parsing of n->str using strtok_r
  do_tokenizing(n->str);

  // will free page after parse returns

  return;
}

void download(node *n) {
  printf("  -->download called on %s.\n", n->str);

  char *page = fetch_fn(n->str);
  assert(page != NULL);
  //printf("PAGE: %s\n", page);

  // page producer
  Mutex_lock(&p_m);
  // page queue is unbounded, so no waiting

  // do pushing
  pages = push(page, pages);
  page_count++;
  
  Cond_signal(&p_fill);
  Mutex_unlock(&p_m);


  // will free page later when it comes off of the page queue

  return;
}

void *downloader(void *arg) {
  printf("***downloader started!\n");

  // loop forever
  while(1) {

    Mutex_lock(&l_m);
    while(link_count == 0) {
      printf("***downloader waiting!\n");
      // wait
      Cond_wait(&l_fill, &l_m);
    }
    // make a new pointer to the top of the stack
    node *next = links;
    // pop the top of the stack (will need to free the node and the string later)
    links = pop(links);
    link_count--;

    // signal links empty
    Cond_signal(&l_empty);
    Mutex_unlock(&l_m);

    // do actual processing
    download(next);

    // free node
    free(next->str);
    free(next);
  
  }

  return 0;
}

void *parser(void *arg) {
/*
 * 1) queue of links (parsers to downloaders): first, push start_url onto queue
 *  - fixed-size
 *  - parsers push links on, wait when full (need cv)
 *  - downloader wait when empty (need cv)
 *  - need mutex
 */

  while(1) {

    printf("***parser started!\n");

    Mutex_lock(&p_m);
    while(page_count == 0) {
      printf("***parser waiting!\n");
      // wait
      Cond_wait(&p_fill, &p_m);
    }
    // make a new pointer to the top of the stack
    node *next = pages;
    // pop the top of the stack (will need to free the node and the string later)
    pages = pop(pages);
    page_count--;

    // signal pages empty: may not need to do this since nobody will be waiting on
    // pages to be reduced in size (unbounded)
    // Cond_signal(&p_empty);
    Mutex_unlock(&p_m);

    // do actual processing
    parse(next);

    // free node
    free(next->str);
    free(next);

    Mutex_lock(&p_m);
    Mutex_lock(&l_m);
    Mutex_lock(&done_m);
    printf("page count: %d\nlink count: %d\n", page_count, link_count);
    if(page_count == 0 && link_count == 0) {
      done = 1;
      Cond_signal(&done_cv);
    }
    Mutex_unlock(&done_m);
    Mutex_unlock(&l_m);
    Mutex_unlock(&p_m);

  }

  return 0;
}

int crawl(char *start_url,
	  int download_workers,
	  int parse_workers,
	  int queue_size,
	  char * (*_fetch_fn)(char *url),
	  void (*_edge_fn)(char *from, char *to)) {

  // initialize our hash set
  table = table_init();

  fetch_fn = _fetch_fn;
  edge_fn = _edge_fn;
  
  // set global for bounded parser queue
  max_link = queue_size;

  // initialize download queue with start_url
  links = push(strdup(start_url), links);
  link_count++;

  pthread_t *did = malloc(download_workers * sizeof(pthread_t));
  pthread_t *pid = malloc(parse_workers * sizeof(pthread_t));

  int i;
  for(i = 0; i < download_workers; i++) {
    Pthread_create(&did[i], NULL, downloader, NULL); 
  }

  for(i = 0; i < parse_workers; i++) {
    Pthread_create(&pid[i], NULL, parser, NULL);     
  }

  // wait for all threads to finish
  //for (i = 0; i < download_workers; i++) {
  //Pthread_join(did[i], NULL); 
  //}

  //for (i = 0; i < parse_workers; i++) {
  //Pthread_join(pid[i], NULL); 
  //}

  // wait for threads to signal completion
  Mutex_lock(&done_m);
  while(done != 1) {
    // wait
    Cond_wait(&done_cv, &done_m);
  }
  
  //return success
  return 0;

  /*
  node *head = NULL;
  head = push(strdup("hola!"), head);
  print(head);
  head = pop(head);
  print(head);
  */
  


}

/*
 * use strtok_r
 * queue: singly-linked list (stack)
 * When your crawler finds a new link in a page, it will do two things: 
 * (1) add the link to the downloaders' work queue, and 
 * (2) return the link (or edge) to the program using your library. You will return the edge by calling the provided edge() function.

 * Your library should use pthreads. You should take an especially close look at pthread_create, pthread_cond_wait, pthread_cond_signal, pthread_mutex_lock, and pthread_mutex_lock.

 *
 *
 * TODO:

 * 1) queue of links (parsers to downloaders): first, push start_url onto queue
 *  - fixed-size
 *  - parsers push links on, wait when full (need cv)
 *  - downloader wait when empty (need cv)
 *  - need mutex

 * 2) queue of pages (downloaders to parsers)
 *  - unbounded
 *  - parser wait when empty (1 cv)
 *  - downloader wait never
 *  - need mutex

 * 3) hash set for pages visited
 *  - hash function (fletcher32)
 *  - "You may copy a hash function from online (e.g., fletcher32 ), as long as a comment in your code references the source URL."

 * 4) Waiting until done
 *  - keep counter to track total work in system
 *  - decrement after taking off queue of pages
 *  - (1 cv, 1 mutex)
 *  - then exit()
 * */

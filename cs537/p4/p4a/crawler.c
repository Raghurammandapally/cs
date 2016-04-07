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

// callbacks
char * (*fetch_fn)(char *url);
void (*edge_fn)(char *from, char *to);

void do_tokenizing(char *orig) {

  char *str = strdup(orig);

  char *curr, *new_str, *word;
  char *saveptr1, *saveptr2;


  while( (curr = strstr(str, "link:")) != NULL) {
    new_str = strdup(curr+1);

    curr = strtok_r(curr, " ", &saveptr1);
    assert(curr != NULL);

    word = strtok_r(curr, ":", &saveptr2);
    word = strtok_r(NULL, ":", &saveptr2);

    printf("addr: %s\n", word);

    free(str);
    str = new_str;
  }

  free(str);

  return;
  /*
  char *str2, *token, *subtoken;
  int j;
  subtoken = strtok_r(token, ":", &saveptr1);
  printf("%s\n", subtoken);
  subtoken = strtok_r(NULL, ":", &saveptr1);
  printf("%s\n", subtoken);
  token = strstr(str1, "link:");
  printf("%s\n", token);
  
  return;

  
  for (j = 1; ; j++) {//, str1 = NULL) {
    //token = strtok_r(str1, "link:", &saveptr1);
    token = strstr(str1, "link:");
    if (token == NULL)
      break;
    printf("%d: %s\n", j, token);
    
    subtoken = strtok_r(token, ":", &saveptr1);

    
    for (str2 = token; ; str2 = NULL) {
      subtoken = strtok_r(str2, " ", &saveptr1);
      if (subtoken == NULL)
	break;
      printf(" --> %s\n", subtoken);
    }
  
  }

  free(str1);
  */
}

void parse(node *n) {
  printf("  parse called on %s.\n", n->str);

  // do some parsing of n->str using strtok_r

  // link producer
  Mutex_lock(&l_m);
  while(link_count == max_link) {
    // wait
    Cond_wait(&l_empty, &l_m);
  }

  // do pushing
  links = push(strdup("ohyah"), links);
  link_count++;
  
  Cond_signal(&l_fill);
  Mutex_unlock(&l_m);


  // will free page later when it comes off of the parse queue
  // free(page);

  return;
}

void download(node *n) {
  printf("  download called on %s.\n", n->str);

  char *page = fetch_fn(n->str);
  assert(page != NULL);
  printf("PAGE: %s\n", page);

  // page producer
  Mutex_lock(&p_m);
  // page queue is unbounded, so no waiting

  // do pushing
  pages = push(page, pages);
  page_count++;
  
  Cond_signal(&p_fill);
  Mutex_unlock(&p_m);


  // will free page later when it comes off of the parse queue
  // free(page);

  return;
}

void *downloader(void *arg) {
  printf("downloader started!\n");

  //while(1) {

  Mutex_lock(&l_m);
  while(link_count == 0) {
    printf("downloader waiting!\n");
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

  //while(1) {
  printf("parser started!\n");

  Mutex_lock(&p_m);
  while(page_count == 0) {
    printf("parser waiting!\n");
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
  


  return 0;
}

int crawl(char *start_url,
	  int download_workers,
	  int parse_workers,
	  int queue_size,
	  char * (*_fetch_fn)(char *url),
	  void (*_edge_fn)(char *from, char *to)) {

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
  for (i = 0; i < download_workers; i++) {
    Pthread_join(did[i], NULL); 
  }

  for (i = 0; i < parse_workers; i++) {
    Pthread_join(pid[i], NULL); 
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

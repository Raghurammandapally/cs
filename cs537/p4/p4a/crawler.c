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

// bounded parse queue
volatile int max_parse = 0;

volatile int download_count = 0;
volatile int parse_count = 0;

// parsers: 2 cv, 1 mutex
pthread_cond_t p_empty = PTHREAD_COND_INITIALIZER;
pthread_cond_t p_fill = PTHREAD_COND_INITIALIZER;
pthread_mutex_t p_m = PTHREAD_MUTEX_INITIALIZER;

// downloaders: 1 cv, 1 mutex
pthread_cond_t d_empty = PTHREAD_COND_INITIALIZER;
pthread_mutex_t d_m = PTHREAD_MUTEX_INITIALIZER;

void *downloader(void *arg) {
  printf("downloader started!\n");
  return 0;
}

void parse(node *n) {
  return;
}

void *parser(void *arg) {
/*
 * 1) queue of links (parsers to downloaders): first, push start_url onto queue
 *  - fixed-size
 *  - parsers push links on, wait when full (need cv)
 *  - downloader wait when empty (need cv)
 *  - need mutex
 */

  Mutex_lock(&p_m);
  while(parse_count == max_parse) {
    // wait
    Cond_wait(&p_empty, &p_m);
  }
  // make a new pointer to the top of the stack
  node *next = pages;
  // pop the top of the stack (will need to free the node and the string later)
  pages = pop(pages);

  // signal
  Cond_signal(&p_fill);
  Mutex_unlock(&p_m);

  // do actual processing
  parse(next);
  
  return 0;
}

int crawl(char *start_url,
	  int download_workers,
	  int parse_workers,
	  int queue_size,
	  char * (*_fetch_fn)(char *url),
	  void (*_edge_fn)(char *from, char *to)) {

  // set global for bounded parser queue
  max_parse = queue_size;
  download_count++;

  // initialize download queue with start_url
  links = push(strdup(start_url), links);

  pages = push(strdup("some text"), pages);
  pages = push(strdup("oh yah"), pages);
  pages = push(strdup("huh?"), pages);
  pages = push(strdup("hrn"), pages);
  pages = push(strdup("mm"), pages);
  pages = push(strdup("some text"), pages);
  print(pages);


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
  

  /*
  char *page = _fetch_fn(start_url);
  assert(page != NULL);
  printf("PAGE: %s\n", page);
  free(page);
  // return 0 on success, and -1 on failure
  return -1;
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

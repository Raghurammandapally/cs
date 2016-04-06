#ifndef __CRAWLER_H
#define __CRAWLER_H

int crawl(char *start_url,
	  int download_workers,
	  int parse_workers,
	  int queue_size,
	  char * (*fetch_fn)(char *url),
	  void (*edge_fn)(char *from, char *to));

// linked_list.c
typedef struct node {
  char* str;
  struct node* next;
} node;

void print(node *);
node *push(char *, node *);
node *pop(node *);




#endif

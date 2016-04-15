#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include "crawler.h"

void f() {
  printf("hello, world!\n");
}


void print(node *head) {
  int i = 0;
  printf("printing LL: \n-----------------\n");
  while(head != NULL) {
    printf("%d: %s\n", i, head->str);
    head = head->next;
    i++;
  }
}

// e.g. head = push(strdup("hello world!", head));
node *push(char *str, char *link, node *head) {
  node *newhead = malloc(sizeof(node));
  assert(newhead != NULL);
  newhead->str = str;
  newhead->link = link;
  newhead->next = head;
  return newhead;
}

// e.g. (do operation on current head
//      head = pop(head);
node *pop(node *head) {
  assert(head != NULL);
  node *newhead = head->next;
  //caller should free the node and the string!
  //------------------------
  //free(head->str);
  //free(head);
  return newhead;
}

// e.g.:
// ------------------------------------
// struct node *head = NULL;
// head = push(strdup("hello"), head);
// head = pop(head);

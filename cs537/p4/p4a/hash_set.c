#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include "crawler.h"
#include <stdint.h>

#define MAX_SIZE 0x10000

// hash returns an int on [0,25] representing the lower-case
// version of the first letter of the string pointed to by
// str
int hash(char *str) {
  int ret = str[0]-65;
  
  if(ret > 25) {
    ret -= 32;
  }
  
  assert(ret >= 0 && ret <= 25);

  return ret;
}

// from https://en.wikipedia.org/wiki/Fletcher%27s_checksum
uint16_t fletcher16( uint8_t const *data, size_t bytes )
{
        uint16_t sum1 = 0xff, sum2 = 0xff;
        size_t tlen;
 
        while (bytes) {
                tlen = bytes >= 20 ? 20 : bytes;
                bytes -= tlen;
                do {
                        sum2 += sum1 += *data++;
                } while (--tlen);
                sum1 = (sum1 & 0xff) + (sum1 >> 8);
                sum2 = (sum2 & 0xff) + (sum2 >> 8);
        }
        /* Second reduction step to reduce sums to 8 bits */
        sum1 = (sum1 & 0xff) + (sum1 >> 8);
        sum2 = (sum2 & 0xff) + (sum2 >> 8);
        return sum2 << 8 | sum1;
}

void print_table(node **table) {
  int i;
  for(i = 0; i < MAX_SIZE; i++) {
    printf("hash table %d\n", i);
    print(table[i]);
  }
}

node **table_init() {
  node **table = malloc(sizeof(node *) * MAX_SIZE);

  assert(table != NULL);

  int i;
  for(i = 0; i < MAX_SIZE; i++) {
    table[i] = NULL;
  }

  return table;
}

void add(node **table, char *str) {
  int slot = fletcher16((uint8_t *) str, strlen(str));

  // check that the hash return is valid
  assert(slot >= 0 && slot < MAX_SIZE);

  table[slot] = push(strdup(str), NULL, table[slot]);
}

int contains(node **table, char *str) {
  int slot = fletcher16((uint8_t *) str, strlen(str));

  // check that the hash return is valid
  assert(slot >= 0 && slot < MAX_SIZE);

  node *ll = table[slot];
  while(ll != NULL) {
    if(strcmp(str, ll->str) == 0) {
      return 1;
    }
    ll = ll->next;
  }

  return 0;
}

int main() {
  //node **table = table_init();

  //add(table, "hello");

  //printf("%d\n", contains(table, "hell"));
  //printf("%d\n", contains(table, "hello"));

  return 0;
}



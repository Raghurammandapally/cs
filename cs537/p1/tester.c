#include <stdio.h>
#include <stdlib.h>

int main() {
  printf("hello, world\n");
  char c;
  char s[130];
  char *ret;

  FILE *fp = fopen("test", "r");
  if(fp == NULL) {
    fprintf(stderr, "failed to open file.\n");
  }

  /*
  while(c != EOF) {
    c = fgetc(fp);
    printf("%c", c);
  }
  */

  ret = fgets(s, 129, fp);

  printf("pre-allocated:\t %p\n", s);
  printf("returned val:\t %p\n", ret);
  
  return 0;

}

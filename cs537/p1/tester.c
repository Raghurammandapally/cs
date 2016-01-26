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

  while(ret != NULL) {
    ret = fgets(s, 129, fp);
    printf("%s", s);
  }

  return 0;

}

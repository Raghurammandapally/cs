#include <stdio.h>
#include <string.h>

//http://stackoverflow.com/questions/4955198/what-does-dereferencing-a-pointer-mean

struct X {
  char str[128];
  char word[128];
};

int compar(const void *p1) {
  struct X *y;

  y = (struct X *) p1;

  printf("%s\n%s\n", y->str, y->word);
  printf("sizeof x: %d\n", sizeof y);

  
  return 0;
}

int main(int argc, char *argv[]) {
  struct X x;
  strcpy(x.str, "hello");
  strcpy(x.word, "world");

  printf("%s\n%s\n", x.str, x.word);
  printf("sizeof x: %d\n", sizeof x);

  compar(&x);
}


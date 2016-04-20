// Do not modify this file. It will be replaced by the grading scripts
// when checking your project.

#include "types.h"
#include "stat.h"
#include "user.h"

void fcn(void *arg);

void
fcn2(void *arg)
{
  //this is the first function
  return;
}

void
fcn(void *arg)
{
  printf(1, "FCN WAS CALLED!\n");
  printf(1, "the location of arg: %x\n", arg);
  printf(1, "the arg was: %d\n", *( (int *) arg));
  exit();
}

void 
test( void (*func)(void *), void *arg)
{
  (*func)(arg);
}

int
main(int argc, char *argv[])
{
  void *stack;
  stack = (void *) malloc(4096);

  int *b;
  b = malloc(sizeof(int));
  *b = 4;

  printf(1, "main:\n");
  printf(1, "--> addr of fcn: %p\n", fcn);

  clone(&fcn, (void *) b, (void *) stack);
  //join( (void **) 0x0);
  exit();
}

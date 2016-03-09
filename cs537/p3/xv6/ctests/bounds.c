#include <stdio.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

#define assert(x) if (x) {} else { \
   printf("%s: %d ", __FILE__, __LINE__); \
   printf("assert failed (%s)\n", # x); \
   printf("TEST FAILED\n"); \
   return 0; \
}

int
main(int argc, char *argv[])
{
  char *arg;

  int fd = open("tmp", O_WRONLY|O_CREAT);
  assert(fd != -1);

  /* at zero */
  arg = (char*) 0x0;
  assert(write(fd, arg, 10) == -1);

  /* within null page */
  arg = (char*) 0x400;
  assert(write(fd, arg, 1024) == -1);

  /* spanning null page and code */
  arg = (char*) 0xfff;
  assert(write(fd, arg, 2) == -1);

  printf("TEST PASSED\n");
  return 0;
}

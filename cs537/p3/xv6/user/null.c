#include "types.h"
#include "user.h"
#include "fcntl.h"

int
main(int argc, char *argv[])
{
  printf(1, "null running...\n-----\n");
  char *shmem = (char *) shmem_access(2);
  printf(1, "got addr %p\n", shmem);

  printf(1, "shmem is currently: %c\n", *shmem);
  // write into shmem
  *shmem = 'c';

  printf(1, "writing to shmem...\n");
  printf(1, "shmem is currently: %c\n", *shmem);
  exit();
}

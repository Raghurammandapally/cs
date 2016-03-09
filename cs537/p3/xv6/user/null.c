#include "types.h"
#include "user.h"
#include "fcntl.h"

int
main(int argc, char *argv[])
{

  int fd = open("tmp", O_WRONLY|O_CREATE);

  char *arg = (char*) 0x0;
  int res = write(fd, arg, 10);
  printf(1, "result: %d\n", res);
  //arg = (char*) 0x400;
  //arg = (char*) 0xfff;

  exit();
}

// Do not modify this file. It will be replaced by the grading scripts
// when checking your project.

#include "types.h"
#include "stat.h"
#include "user.h"

void
fcn(void *arg)
{
  return;
}

int
main(int argc, char *argv[])
{
  clone(fcn, (void *) 0x0, (void *) 0x0);
  join( (void **) 0x0);
  exit();
}

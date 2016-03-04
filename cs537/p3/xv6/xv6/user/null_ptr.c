#include "types.h" //NULL
#include "user.h"  //printf

int main() {
  printf(1, "initializing *p to NULL...\n");
  
  int *p = NULL;
  printf(1, "&p: %p\n", p);
  
  printf(1, "printing *p...\n");

  //null dereference
  printf(1, "%d\n", *p);

  return 0;
}
/*
$ null_ptr
initializing *p to NULL...
&p: 0
printing *p...
-2082109099
pid 5 null_ptr: trap 14 err 5 on cpu 1 eip 0xffffffff addr 0xffffffff--kill proc
*/

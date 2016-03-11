#include "types.h"
#include "stat.h"
#include "user.h"

void
test_failed()
{
	printf(1, "TEST FAILED\n");
	exit();
}

void
test_passed()
{
 printf(1, "TEST PASSED\n");
 exit();
}

int
main(int argc, char *argv[])
{
	void *ptr;
 	int n;
	int i;
	
	for (i = 0; i < 4; i++) {
		n = shmem_count(i);
		if (n != 0) {
			test_failed();
		}
	}

	int pid = fork();
	if (pid < 0) {
		test_failed();
	}
	else if (pid == 0) {

	  printf(1, "pid 0! (child process)\n");
		for (i = 0; i < 4; i++) {
			ptr = shmem_access(i);
			if (ptr == NULL) {
				test_failed();
			}
			printf(1, "shmem_count: %d\n", shmem_count(i));
			printf(1, "addr: %d\n", ptr);
		}
		printf(1, "child exiting.\n");
		exit();	
	}
	else {
	  printf(1, "pid %d! (parent process)\n", pid);
	  printf(1, "parent about to wait...\n");

		wait();
		printf(1, "parent done waiting.\n");
		for (i = 0; i < 4; i++) {
			n = shmem_count(i);
			if (n != 0) {
				test_failed();
			}
			printf(1, "passed with 0 for shmem_count\n");
		}
	}
	
	test_passed();
	exit();
}

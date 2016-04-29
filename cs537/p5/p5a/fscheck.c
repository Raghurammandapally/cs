#include <errno.h>
#include <stdio.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <sys/mman.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>
#include <malloc.h>

/*
Your checker should read through the file system image and determine the consistency of a number of things, including the following. When one of these does not hold, print the error message (also shown below) and exit immediately.

Each inode is either unallocated or one of the valid types (T_FILE, T_DIR, T_DEV). ERROR: bad inode.
For in-use inodes, each address that is used by inode is valid (points to a valid datablock address within the image). Note: must check indirect blocks too, when they are in use. ERROR: bad address in inode.
Root directory exists, and it is inode number 1. ERROR MESSAGE: root directory does not exist.
Each directory contains . and .. entries. ERROR: directory not properly formatted.
Each .. entry in directory refers to the proper parent inode, and parent inode points back to it. ERROR: parent directory mismatch.
For in-use inodes, each address in use is also marked in use in the bitmap. ERROR: address used by inode but marked free in bitmap.
For blocks marked in-use in bitmap, actually is in-use in an inode or indirect block somewhere. ERROR: bitmap marks block in use but it is not in use.
For in-use inodes, any address in use is only used once. ERROR: address used more than once.
For inodes marked used in inode table, must be referred to in at least one directory. ERROR: inode marked use but not found in a directory.
For inode numbers referred to in a valid directory, actually marked in use in inode table. ERROR: inode referred to in directory but marked free.
Reference counts for regular files match the number of times file is referred to in directories (i.e., hard links work correctly). ERROR: bad reference count for file.
No extra links allowed for directories (each directory only appears in one other directory). ERROR: directory appears more than once in file system.

*/

int main(int argc, char *argv[]) {
  int fd;
  struct stat fstruct;
  char *fdata;
  
  if(argc != 2) {
    fprintf(stderr, "Invalid number of arguments!\n");
    return 1;
  }

  fd = open(argv[1], O_RDONLY);

  //open() and creat() return the new file descriptor, or -1 if an error occurred (in which
  //case, errno is set appropriately).
  if(fd < 0) {
    fprintf(stderr, "image not found.\n");
    return 1;
  }

  //int fstat(int fd, struct stat *buf);
  if(fstat(fd, &fstruct) < 0) {
    fprintf(stderr, "error in fstat.\n");
    return 1;
  }

  //void *mmap(void *addr, size_t length, int prot, int flags,
  //  int fd, off_t offset);
  fdata = mmap(NULL, fstruct.st_size, PROT_READ,
	       MAP_PRIVATE, fd, 0);
  
  if(fdata == MAP_FAILED) { // (void *) -1
    fprintf(stderr, "error in mmap.\n");
    return 1;
  }

  //printf("file:\n---------------\n%s", fdata);
  //printf("first char: %c\n", *fdata);

  return 0;

}

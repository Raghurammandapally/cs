#include <string.h>
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
#include <inttypes.h>
#include <stdint.h>

// Definitions from xv6
typedef unsigned char uchar;

// include/stat.h
#define T_DIR  1   // Directory
#define T_FILE 2   // File
#define T_DEV  3   // Special device

//include/fs.h
// Block 0 is unused.
// Block 1 is super block.
// Inodes start at block 2.
#define ROOTINO 1  // root i-number
#define BSIZE 512  // block size

// File system super block
struct superblock {
  uint size;         // Size of file system image (blocks)
  uint nblocks;      // Number of data blocks
  uint ninodes;      // Number of inodes.
};

#define NDIRECT 12
#define NINDIRECT (BSIZE / sizeof(uint))
#define MAXFILE (NDIRECT + NINDIRECT)

// On-disk inode structure
struct dinode {
  // 1: T_DIR
  // 2: T_FILE
  // 3: T_DEV
  short type;           // File type
  short major;          // Major device number (T_DEV only)
  short minor;          // Minor device number (T_DEV only)
  short nlink;          // Number of links to inode in file system
  uint size;            // Size of file (bytes)
  uint addrs[NDIRECT+1];   // Data block addresses
};

// Inodes per block.
#define IPB           (BSIZE / sizeof(struct dinode))

// Block containing inode i
#define IBLOCK(i)     ((i) / IPB + 2)

// Bitmap bits per block
#define BPB           (BSIZE*8)

// Block containing bit for block b
#define BBLOCK(b, ninodes) (b/BPB + (ninodes)/IPB + 3)

// Directory is a file containing a sequence of dirent structures.
#define DIRSIZ 14

struct dirent {
  ushort inum;
  char name[DIRSIZ];
};

#define NUM_DIRS BSIZE/sizeof(struct dirent)

struct dir {
  struct dirent dirs[NUM_DIRS];
};

struct indirect_dir {
  struct dir *dirs[NINDIRECT];
};

#define NUM_BYTES BSIZE/sizeof(uchar)
struct bitmap {
  uchar bits[NUM_BYTES]
};

/*
Your checker should read through the file system image and determine the consistency of a number of things, including the following. When one of these does not hold, print the error message (also shown below) and exit immediately.

*/

// pointer to location in memory that file is mapped to
char *fdata;

struct dirent *
get_dirent(struct dir *dir, int num)
{
  return (struct dirent *) ( (uintptr_t) dir + (num * sizeof(struct dirent)) );
}

void *
get_block(uint blk)
{
  return (void *)( (uintptr_t) fdata + (BSIZE * blk) );
}

int
isvalid(uint addr, struct superblock *sb)
{
  return addr > 1 + (sb->ninodes/IPB) && addr < sb->nblocks;
}

int
main(int argc, char *argv[])
{
  int fd;
  struct stat fstruct;
  
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

  // block 1 is superblock
  struct superblock *sb;
  sb = (struct superblock *) ( ((intptr_t) fdata) + BSIZE);

  // bitmap
  void *bmp = (void *) ( (intptr_t) fdata + (512 * (sb->ninodes/IPB + 3)));
  

  struct dinode *di = (struct dinode *) ( (intptr_t) fdata + 1024 );
  int i;
  for(i = 0; i < sb->ninodes; i++) {
    //Each inode is either unallocated or one of the valid types (T_FILE, T_DIR, T_DEV). ERROR: bad inode.
    if(di->type < 0 || di->type > T_DEV) {
      fprintf(stderr, "bad inode.\n");
      return 1;
    }

    if(di->type > 0) {
      // For in-use inodes, each address that is used by inode is valid (points to a valid datablock address within the image). Note: must check indirect blocks too, when they are in use. ERROR: bad address in inode.
      int data;
      for(data = 0; data < NDIRECT; data++) {
	if(di->addrs[data] == 0) {
	  continue;
	}
	//if(di->addrs[data] <= 1 + (sb->ninodes/IPB) || di->addrs[data] >= sb->nblocks) {
	if(!isvalid(di->addrs[data], sb)) {
	  fprintf(stderr, "bad address in inode.\n");
	  return 1;
	}
      }
      // now data == NDIRECT; check if we are using indirect block
      if(di->addrs[data]) {
	uint *indirect = (uint *) ( (uintptr_t) fdata + (BSIZE * di->addrs[data]));
	int j;
	for(j = 0; j < NINDIRECT; j++) {
	  if(*indirect == 0) {
	    continue;
	  }

	  if(!isvalid(*indirect, sb)) {
	    fprintf(stderr, "bad address in inode.\n");
	    return 1;
	  }

	  indirect++;
	}
      }
    }
    // move pointer to next dinode
    di++;
  }
  
  di = (struct dinode *) ( (intptr_t) fdata + 1024 + sizeof(struct dinode) );
  // Root directory exists, and it is inode number 1. ERROR MESSAGE: root directory does not exist.
  if(di->type != 1) {
    fprintf(stderr, "root directory does not exist.\n");
    return 1;
  }

  // Each directory contains . and .. entries. ERROR: directory not properly formatted.
  di = (struct dinode *) get_block(2);
  for(i = 0; i < sb->ninodes; i++) {
    if(di->type != T_DIR) {
      di++;
      continue;
    }

    int has_dot;
    int has_dotdot;

    int block;
    struct dir *de;
    for(block = 0; block < NDIRECT && di->addrs[block]; block++) {
      de = (struct dir *) get_block(di->addrs[block]);
      int k;
      for(k = 0; k < NUM_DIRS; k++) {
	struct dirent *dirent;
	dirent = get_dirent(de, k);
	if(strcmp(dirent->name, ".") == 0) {
	  has_dot = 1;
	} else if (strcmp(dirent->name, "..") == 0) {
	  has_dotdot = 1;
	}
      }
    }
    
    //now block == NDIRECT, so we are on the indirect block
    if(di->addrs[block]) {
      struct indirect_dir *in_dir = (struct indirect_dir *) get_block(di->addrs[block]);
      int l;
      for(l = 0; l < NINDIRECT; l++) {
	struct dir *dir = in_dir->dirs[l];
	int k;
	for(k = 0; k < NUM_DIRS; k++) {
	  struct dirent *dirent;
	  dirent = get_dirent(de, k);
	  if(strcmp(dirent->name, ".") == 0) {
	    has_dot = 1;
	  } else if (strcmp(dirent->name, "..") == 0) {
	    has_dotdot = 1;
	  }
	}
      }
    }

    if(!has_dot || !has_dotdot) {
      fprintf(stderr, "directory not properly formatted.\n");
      return 1;
    }
    
    di++;
  }


  struct bitmap *bits = (struct bitmap *) get_block(28);
  uchar used[BPB];
  for(i = 0; i < NUM_BYTES; i++) {
    uchar u = bits->bits[i];
    int j;
    for(j = 0; j < 8; j++) {
      used[(i*8)+j] = u % 2;
      u = u >> 1;
    }
  }
  int count = 0;
  for(i = 0; i < BPB; i++) {
    if(used[i]) count++;
    printf("%d ", used[i]);
  }
  printf("count: %d\n", count);
  /*
  struct dinode *di = (struct dinode *) get_block(2);
  for(i = 0; i < sb->ninodes; i++) {
    if(di->type > 0 && di->type > T_DEV) {
      fprintf(stderr, "bad inode.\n");
      return 1;
    }

    di++;
  }
  */

// Each .. entry in directory refers to the proper parent inode, and parent inode points back to it. ERROR: parent directory mismatch.
  /*
    loop over inodes i
      if not T_DIR or i == 1:
        continue
      
       
  */
      
      
      
// For in-use inodes, each address in use is also marked in use in the bitmap. ERROR: address used by inode but marked free in bitmap.  
// For blocks marked in-use in bitmap, actually is in-use in an inode or indirect block somewhere. ERROR: bitmap marks block in use but it is not in use.
// For in-use inodes, any address in use is only used once. ERROR: address used more than once.
// For inodes marked used in inode table, must be referred to in at least one directory. ERROR: inode marked use but not found in a directory.
// For inode numbers referred to in a valid directory, actually marked in use in inode table. ERROR: inode referred to in directory but marked free.
// Reference counts for regular files match the number of times file is referred to in directories (i.e., hard links work correctly). ERROR: bad reference count for file.
// No extra links allowed for directories (each directory only appears in one other directory). ERROR: directory appears more than once in file system.
  
  printf("sizeof uint: %d\n", sizeof(uint));
  
  return 0;

}




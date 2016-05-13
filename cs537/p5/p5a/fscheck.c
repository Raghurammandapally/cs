// mmap
#include <sys/mman.h>

// fstat
#include <sys/types.h>
#include <sys/stat.h>
#include <unistd.h>

// fprintf
#include <stdio.h>

// open
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>

// uintptr_t
#include <stdint.h>

// exit
# include <stdlib.h>

// fs info including structs
#include "fs.h"
// structs:
//   superblock
//   dinode
//   dirent

// macros
/******************************************************************/
#define DIE(str) fprintf(stderr, str); exit(1);
#define DUMP_INT(expr) printf(#expr ": %d\n", expr);
#define NBYTES BSIZE/sizeof(char)
#define NUMDIRENTS BSIZE/sizeof(struct dirent)
#define T_DIR 1
#define T_DEV 2
#define T_FILE 3

// structs
/******************************************************************/
// bitmap
struct bitmap {
  unsigned char bytes[NBYTES];
};

// indirect pointer to a block full of references
struct indirect {
  uint addrs[NINDIRECT];
};

// directory is full of directory entries
struct dir {
  struct dirent ents[NUMDIRENTS];
};

// inode block
struct iblock {
  struct dinode inodes[IPB];
};

/*
// File system super block
struct superblock {
  uint size;         // Size of file system image (blocks)
  uint nblocks;      // Number of data blocks
  uint ninodes;      // Number of inodes.
};

// On-disk inode structure
struct dinode {
  short type;           // File type
  short major;          // Major device number (T_DEV only)
  short minor;          // Minor device number (T_DEV only)
  short nlink;          // Number of links to inode in file system
  uint size;            // Size of file (bytes)
  uint addrs[NDIRECT+1];   // Data block addresses
};

struct dirent {
  ushort inum;
  char name[DIRSIZ];
};
*/

/******************************************************************/
// pointer to (block 0) file in memory after mmap
void *fdata;
// location of superblock
struct superblock *sb;

// prototypes
/******************************************************************/
void init();
int block_in_use(uint);
int inode_in_use(uint);
int bitmap_block_used(struct bitmap *, uint);
void *get_block(uint);
void test();
struct indirect *get_indirect(struct dinode *);
struct dinode *get_inode(uint);
int is_valid_addr(uint);
int dir_contains(struct dinode *, char *);
int dirent_contains(struct dir *, char *);
struct dir *get_dir(struct dinode *, uint blk);

// main method
/******************************************************************/
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

  // initialize superblock, etc.
  init();

  // run checks
  test();

  // if none of them failed, we are error-free!
  return 0;

}

// do some initialization
void
init()
{
  // set up superblock
  sb = (struct superblock *) get_block(1);
}  

// return a pointer to the blk'th block, offset from fdata
void *
get_block(uint blk)
{
  uint ret = (uintptr_t) fdata + (BSIZE * blk);
  return (void *) ((uintptr_t) fdata + (BSIZE * blk));
}

// is block i in use according to the bitmap?
int
block_in_use(uint blk)
{
  struct bitmap *bmp = (struct bitmap *) get_block(BBLOCK(blk, sb->ninodes));
  return bitmap_block_used(bmp, blk);
}

// is inode i in use according to the bitmap?
int
inode_in_use(uint i)
{
  struct dinode *di = get_inode(i);
  return di->type != 0;
}

// is blk i of the bitmap marked as a 0 or a 1?
int
bitmap_block_used(struct bitmap *bmp, uint blk)
{
  uint byte = (blk % BPB) / 8;
  uint bit = blk % 8;
  uint shift = bmp->bytes[byte] >> bit;
  return shift % 2 == 1;
}

// return a pointer to the dinode whose number is given by inode
struct dinode *
get_inode(uint inode)
{
  struct iblock *iblk = (struct iblock *) get_block(IBLOCK(inode));
  return &(iblk->inodes[inode % IPB]);
}

// return a pointer to the indirect struct for the inode whose number
// is given by inode
// returns NULL if there is no indirect block given
struct indirect *
get_indirect(struct dinode *di)
{
  if(di->addrs[NDIRECT] == 0) {
    return NULL;
  } else {
    return (struct indirect *) get_block(di->addrs[NDIRECT]);
  }
}

// given an inode and the number of which dir block to look at
// (should be between 0 and NDIRECT + NINDIRECT), return the struct dir
// MUST check beforehand whether the addr specified is non-zero
struct dir *
get_dir(struct dinode *di, uint blk)
{
  if(blk < NDIRECT) {
    if(!di->addrs[blk])
      return NULL;
    return (struct dir *) get_block(di->addrs[blk]);
  } else {
    struct indirect *ind = get_indirect(di);
    if(ind == NULL || !ind->addrs[blk-NDIRECT])
      return NULL;
    return (struct dir *) get_block(ind->addrs[blk-NDIRECT]);
  }
}
    

// check whether addr is valid (points to a valid datablock address within the image)
int
is_valid_addr(uint addr)
{
  return addr < sb->size && sb->size - sb->nblocks <= addr;
}

// check whether the dir whose inode is given by di contains a pointer to the inode
// with name str
// if the dir does contain it, return the inode number; otherwise return 0
int
dir_contains(struct dinode *di, char *str)
{
  int i;
  for(i = 0; i < NDIRECT; i++) {
    // only check addresses that are non-zero
    if(!di->addrs[i])
      break;
    
    struct dir *dir = (struct dir *) get_block(di->addrs[i]);

    // if we find the string, the go ahead and return
    int ret = dirent_contains(dir, str);
    if(ret) 
      return ret;
  }
  // else, we have failed
  return 0;
}

// check whether the dir given by de contains a dirent with name str
// if the dir does contain it, return the inode number; otherwise return 0
int
dirent_contains(struct dir *d, char *str)
{
  int i;
  for(i = 0; i < NUMDIRENTS; i++) {
    // if the two strings are equal, return true
    if(strncmp(str, d->ents[i].name, DIRSIZ) == 0) {
      return d->ents[i].inum;
    }
  }
  // otherwise, return false
  return 0;
}

// check if the inode pointed to has a valid type
int
is_valid_inode_type(struct dinode *inode)
{
  return inode->type == 0 || // unallocated
    inode->type == T_DIR ||
    inode->type == T_DEV || 
    inode->type == T_FILE;
}

// checks
/******************************************************************/
//Each inode is either unallocated or one of the valid types (T_FILE, T_DIR, T_DEV). ERROR: bad inode.
void
check_inode_types()
{
  int i;
  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *inode = get_inode(i);
    if(!is_valid_inode_type(inode)) {
      DIE("ERROR: bad inode.\n");
    }
  }
}

//For in-use inodes, each address that is used by inode is valid (points to a valid datablock address within the image). Note: must check indirect blocks too, when they are in use. ERROR: bad address in inode.
void
check_inode_addrs()
{
  int i, j;
  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *inode = get_inode(i);
    if(inode->type == 0)
      continue;
    for(j = 0; j < NDIRECT; j++) {
      if(inode->addrs[j] != 0 && !is_valid_addr(inode->addrs[j])) {
	DIE("ERROR: bad address in inode.\n");
      }
    }

    struct indirect *ind;
    if( (ind = get_indirect(inode)) != NULL ) {
      for(j = 0; j < NINDIRECT; j++) {
	if(ind->addrs[j] != 0 && !is_valid_addr(ind->addrs[j])) {
	  DIE("ERROR: bad address in inode.\n");
	}
      }
    }
  }
}    

//Root directory exists, and it is inode number 1. ERROR MESSAGE: root directory does not exist.
void
check_root()
{
  struct dinode *root = get_inode(1);

  if(root->type != 1 || (dir_contains(root, ".") != 1) || (dir_contains(root, "..") != 1))  {
    DIE("ERROR: root directory does not exist.\n");
  }
}

//Each directory contains . and .. entries. ERROR: directory not properly formatted.
void
check_dirs_rel_links()
{
  int i;
  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *di = get_inode(i);
    if(di->type != T_DIR)
      continue;
    if(!(dir_contains(di, ".") && dir_contains(di, ".."))) {
      DIE("ERROR: directory not properly formatted.\n");
    }
  }
}

//Each .. entry in directory refers to the proper parent inode, and parent inode points back to it. ERROR: parent directory mismatch.
void
check_parent_refs()
{
  int i,j,k;
  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *di = get_inode(i);
    if(di->type != T_DIR) // not a dir or the root dir
      continue;
    // loop over the folders inside this folder
    struct dir *dir;
    for(j = 0; j < NDIRECT + NINDIRECT; j++) {
      if( (dir = get_dir(di, j)) == NULL )
	continue;
      struct dirent de;
      for(k = 0; k < NUMDIRENTS; k++) {
	de = dir->ents[k];
	if( de.inum == 0 || ( strncmp(de.name, ".", DIRSIZ) == 0 ) || ( strncmp(de.name, "..", DIRSIZ) == 0) ) { // check if it's . or ..
	  continue;
	}
	struct dinode *subdir = get_inode(de.inum);
	if(subdir->type != T_DIR) {
	  continue;
	}
	int ret = dir_contains(subdir, "..");
        if(ret == 0 || ret != i) {
	  DIE("ERROR: parent directory mismatch.\n");
	}
      }
    }
  }
}

//For in-use inodes, each address in use is also marked in use in the bitmap. ERROR: address used by inode but marked free in bitmap.
void
check_inode_addrs_used()
{
  int i, j;
  struct dinode *di;

  // first, check the major parts of the os
  for(i = 1; i < sb->size - sb->nblocks; i++) {
    if(!block_in_use(i)) {
      //DIE("address used by inode but marked free in bitmap.\n");
    }
  }
  
  // now check data blocks referenced by inodes
  for(i = 0; i < sb->ninodes; i++) {
    di = get_inode(i);
    if(di->type == 0)
      continue;
    for(j = 0; j < NDIRECT; j++) {
      if(di->addrs[j] && !block_in_use(di->addrs[j])) {
	DIE("ERROR: address used by inode but marked free in bitmap.\n");
      }
    }
    struct indirect *ind;
    if( (ind = get_indirect(di)) != NULL) {
      for(j = 0; j < NINDIRECT; j++) {
	if(ind->addrs[j] && !block_in_use(ind->addrs[j])) {
	  DIE("ERROR: address used by inode but marked free in bitmap.\n");
	}
      }
    }
  }      
}

//For blocks marked in-use in bitmap, actually is in-use in an inode or indirect block somewhere. ERROR: bitmap marks block in use but it is not in use.
void
check_bitmap_addrs_used()
{
  uint used[sb->size];
  int i, j;
  struct dinode *di;

  // first, check the major parts of the os
  for(i = 0; i < sb->size - sb->nblocks; i++) {
    used[i]++;
  }
  
  // now check data blocks referenced by inodes
  for(i = 0; i < sb->ninodes; i++) {
    di = get_inode(i);
    if(di->type == 0)
      continue;
    for(j = 0; j < NDIRECT; j++) {
      if(di->addrs[j]) {
	used[di->addrs[j]]++;
      }
    }
    struct indirect *ind;
    if( (ind = get_indirect(di)) != NULL) {
      // update the counter for the indirect block itself
      used[di->addrs[j]]++;

      for(j = 0; j < NINDIRECT; j++) {
	if(ind->addrs[j]) {
	  used[ind->addrs[j]]++;
	}
      }
    }
  }

  // now check them all
  for(i = 0; i < sb->size; i++) {
    if(!used[i] && block_in_use(i)) {
      DIE("ERROR: bitmap marks block in use but it is not in use.\n");
    }
  }
}

//For in-use inodes, any address in use is only used once. ERROR: address used more than once.
void
check_addrs_used_once()
{
  unsigned int used[sb->size];

  int i, j;

  for(i = 0; i < sb->size; i++) {
    used[i] = 0;
  }

  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *inode = get_inode(i);
    if(inode->type == 0)
      continue;
    for(j = 0; j < NDIRECT; j++) {
      if(inode->addrs[j] != 0) {
	used[inode->addrs[j]]++;
      }
    }

    struct indirect *ind;
    if( (ind = get_indirect(inode)) != NULL ) {
      for(j = 0; j < NINDIRECT; j++) {
	if(ind->addrs[j] != 0) {
	  used[ind->addrs[j]]++;
	}
      }
    }
  }

  for(i = 0; i < sb->size; i++) {
    //printf("used[%d]: %d\n", i, used[i]);
    if(used[i] > 1) {
      DIE("ERROR: address used more than once.\n");
    }
  }
}

//For inodes marked used in inode table, must be referred to in at least one directory. ERROR: inode marked use but not found in a directory.
//For inode numbers referred to in a valid directory, actually marked in use in inode table. ERROR: inode referred to in directory but marked free.
void
check_inode_usage()
{
  unsigned int used[sb->ninodes];

  int i, j, k;

  // initialize
  for(i = 0; i < sb->ninodes; i++) {
    used[i] = inode_in_use(i);
  }

  // loop over dirents and decrement index of inum with each reference
  for(i = 0; i < sb->ninodes; i++) {
    struct dinode *di = get_inode(i);
    if(di->type != T_DIR) // not a dir or the root dir
      continue;
    // loop over the folders inside this folder
    struct dir *dir;
    for(j = 0; j < NDIRECT + NINDIRECT; j++) {
      if( (dir = get_dir(di, j)) == NULL )
	continue;
      struct dirent de;
      for(k = 0; k < NUMDIRENTS; k++) {
	de = dir->ents[k];
	if( de.inum == 0 || ( strncmp(de.name, ".", DIRSIZ) == 0 ) || ( strncmp(de.name, "..", DIRSIZ) == 0) ) { // check if it's . or ..
	  continue;
	}
	used[de.inum]--;
      }
    }
  }
  
  // start at 2 because 0 is unused and 1 is unreferenced
  for(i = 2; i < sb->ninodes; i++) {
    if(used[i] == 1) {
      DIE("ERROR: inode marked use but not found in a directory.\n");
    } else if (used[i] == -1) {
      DIE("ERROR: inode referred to in directory but marked free.\n");
    }
  }
}

//Reference counts (number of links) for regular files match the number of times file is referred to in directories (i.e., hard links work correctly). ERROR: bad reference count for file.
//No extra links allowed for directories (each directory only appears in one other directory). ERROR: directory appears more than once in file system.

void
test()
{
  check_inode_types();
  check_inode_addrs();
  check_dirs_rel_links();
  check_root();
  check_parent_refs();
  check_inode_addrs_used();
  check_bitmap_addrs_used();
  check_addrs_used_once();
  check_inode_usage();
  

  /*  
  DUMP_INT(sb->size - sb->nblocks);

  DUMP_INT(block_in_use(0));
  DUMP_INT(block_in_use(454));
  DUMP_INT(block_in_use(453));
  DUMP_INT(block_in_use(500));

  printf("root contains .: %d\n", dir_contains((struct dinode *) get_inode(1), "."));
  printf("root contains ..: %d\n", dir_contains((struct dinode *) get_inode(1), ".."));
  printf("root contains onomanopoeoieo: %d\n", dir_contains((struct dinode *) get_inode(1), "onomanopoeoiaeoia"));

  DUMP_INT(sb->nblocks);
  DUMP_INT(sb->size);

  DUMP_INT(is_valid_addr(29));
  DUMP_INT(is_valid_addr(1023));
  DUMP_INT(is_valid_addr(28));
  DUMP_INT(is_valid_addr(1024));
  
  printf("type of inode 0: %d\n", get_inode(0)->type);
  printf("type of inode 1: %d\n", get_inode(1)->type);
  printf("type of inode 2: %d\n", get_inode(2)->type);

  int i;
  for(i = 0; i < sb->size; i++) {
    printf("%d ", block_in_use(i));
    if(i % 8 == 7) {
      printf("\n");
    }
  }
  */
}

#include <sys/types.h>
#include <dirent.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include "path.c"
#include <assert.h>
// 128 for chars, 1 for \n and 1 for \0
#define MAX_LEN 130

const char error_message[30] = "An error has occurred\n";

void writeout(char *str) {
  write(STDOUT_FILENO, str, strlen(str));
}

void err() {  
  write(STDERR_FILENO, error_message, strlen(error_message)); 
}

int main() {
  struct path p = {.count = 0, .paths = NULL};

  char *greeting = "whoosh> ";
  char *init_paths[2];

  // has to start at 1 because update_paths starts there
  init_paths[1] = "/bin";

  // have to pass 1+1 == 2 since we calculate argc-1 in update_path
  update_path(&p, init_paths, 2);

  char input[MAX_LEN];




  //*****************************
  //beginwhile
  //*****************************  
  while(1) {
    writeout(greeting);
    fgets(input, MAX_LEN, stdin);
  
    if(input[strlen(input)-1] != '\n') {
      err();
    } else {
      input[strlen(input)-1] = '\0';
    }

    int argc = 0;
    int ptr = 0;
    int in_word = 0;
    while(input[ptr] != '\0') {
      if(input[ptr] != ' ' && !in_word) {
	argc++;
	in_word = 1;
      } else if (input[ptr] == ' ' && in_word) {
	in_word = 0;
      }
      ptr++;
    }

    if(argc == 0) {
      // not an error
      continue;
    }
  
    char **argv;
    argv = malloc( sizeof(char*) * (argc+1) );
    argv[0] = strtok(input, " ");
    for(ptr = 1; ptr <= argc; ptr++) {
      argv[ptr] = strtok(NULL, " ");
    }

    //these should be the correct params for execv now
    assert(argv[argc] == NULL);

    // check for built in programs:
    //   -exit
    //   -cd
    //   -pwd
    //   -path
    if(strcmp(argv[0], "exit") == 0) {
      exit(0);
    } else if( strcmp(argv[0], "cd") == 0 ) {
      char *dir;

      if(argc > 2) {
	err();
	exit(1);
      } else if(argc == 1) {
	//[optionalSpace]cd[optionalSpace]      
	dir = getenv("HOME");
	assert(dir != NULL);
      
      } else {
	//[optionalSpace]cd[oneOrMoreSpace]dir[optionalSpace]
	dir = argv[1];
      }
      chdir(dir);
    } else if( strcmp(argv[0], "pwd") == 0 ) {
      //[optionalSpace]pwd[optionalSpace]
      if(argc > 1) {
	err();
	exit(1);
      }
      char *wd = getcwd(NULL, 0);
      writeout(wd);
      writeout("\n");
      free(wd);
    } else if( strcmp(argv[0], "path") == 0 ) {
      //[optionalSpace]path[oneOrMoreSpace]dir[optionalSpace] (and possibly
      //more directories, space separated)
      update_path(&p, argv, argc);
      //print_paths(&p);
    } else {
      //search for executables in path dirs
      
    }

    // Clean up input argv
    free(argv);







  }
  //********************************
  //endwhile
  //********************************

  // clean up
  clear_paths(&p);

  exit(0);
}

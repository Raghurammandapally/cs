#include <sys/wait.h>
#include <sys/stat.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
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

int main(int carg, char** varg) {
  if(carg > 1) {
    err();
    exit(1);
  }
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
      while(input[strlen(input)-1] != '\n') {
	fgets(input, MAX_LEN, stdin);
      }
      err();
      continue;
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

    int redir = 0;
    int last_redir = 0;
    for(ptr = 1; ptr < argc; ptr++) {
      if(strcmp(argv[ptr], ">") == 0) {
	redir++;
	last_redir = ptr;
      }
    }
    // do error checking for redirection here, but closing/opening of files
    // in child process
    if(redir > 1) {
      err();
      continue;
    } else if (redir == 1) {
      if(last_redir != argc-2) {
	err();
	continue;
      } else {
      }
    }

    //these should be the correct params for execv now
    assert(argv[argc] == NULL);

    // check for built in programs:
    //   -exit
    //   -cd
    //   -pwd
    //   -path
    if(strcmp(argv[0], "exit") == 0) {
      if(argc>1) {
	err();
	exit(1);
      }
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
      int res = chdir(dir);
      if(res != 0) {
	err();
	continue;
      }
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
      struct stat *buf;
      buf = malloc(sizeof(struct stat));
      int res;
      //keep track of if the exec was found
      int found = 0;
      //search for executables in path dirs
      for(ptr = 0; ptr < p.count; ptr++) {
	//+1 for '/' in middle
	int len = strlen(p.paths[ptr]) + strlen(argv[0]) + 1;
	//+1 for \0 at end
	char *dest = malloc( sizeof(char) * (len + 1));
	strcpy(dest, p.paths[ptr]);
	dest[strlen(p.paths[ptr])] = '/';
	strcpy( dest + strlen(p.paths[ptr]) + 1 , argv[0]);

	res = stat(dest, buf);
	if(res != 0) {
	  //err();
	  free(dest);
	  continue;
	} else {
	  argv[0] = dest;
	  int rc = fork();
	  if(rc == 0) { // child

	    if(redir > 0) {
	      //need to:
	      // - close stdout and stderr
	      // - open new stdout and stderr files
	      char *prefix = argv[last_redir + 1];
	      // 4 for ".out" and 1 for \0
	      char *out = malloc(strlen(prefix) + 5);
	      strcpy(out, prefix);
	      strcat(out, ".out");
	      char *errout = malloc(strlen(prefix) + 5);
	      strcpy(errout, prefix);
	      strcat(errout, ".err");

	      close(STDOUT_FILENO);
	      if( open(out,
		   O_CREAT | O_TRUNC | O_WRONLY,
		       S_IRUSR | S_IWUSR) != 0) {
		err();
		exit(1);
	      }

	      close(STDERR_FILENO);
	      if( open(errout,
		   O_CREAT | O_TRUNC | O_WRONLY,
		       S_IRUSR | S_IWUSR) != 0) {
		err();
		exit(1);
	      }

	      // - set argv[redir] = NULL
	      argv[last_redir] = NULL;
	      // - set argc = redir
	      argc = last_redir;
	    }

	    execv(argv[0], argv);
	    // if these lines of code are executed, an error has occurred
	    err();
	    exit(1);
	  } else if (rc > 0) { //parent
	    int cpid = (int) wait(NULL);
	    found = 1;
	    break;
	  } else { //error
	    err();
	    exit(1);
	  }
	}

      }
      if(!found) {
	err();
	continue;
      }
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

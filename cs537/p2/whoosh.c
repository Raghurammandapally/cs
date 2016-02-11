#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
// 128 for chars, 1 for \n and 1 for \0
#define MAX_LEN 130

const char error_message[30] = "An error has occurred\n";

struct path {
  char **paths;
  int count;
};

void err() {  
  write(STDERR_FILENO, error_message, strlen(error_message)); 
}

void clear_paths(struct path *p) {
  int i;
  //argc/argv are passed directly from the cli, so they include:
  // argc[0]: path
  // argc[1]: (first dir)
  // ...
  // argc[argv-1]: (last dir)

  // free all old pointers
  for(i = 0; i < p->count; i++) {
    free(p->paths[i]);
  }
  if(p->paths != NULL) {
    free(p->paths);
    p->paths = NULL;
  }
}

int update_path(struct path *p, char **argc, int argv) {
  clear_paths(p);

  int i;
  // set count var before potentially returning
  p->count = argv-1;

  if(argv == 1) {
    //no need to allocate space
    return 0;
  }

  // add all new *chars
  p->paths = malloc((argv-1)*sizeof(char*));
  if(p->paths == NULL) {
    return 1;
  }
  for(i = 1; i < argv; i++) {
    p->paths[i-1] = strdup(argc[i]);
    if(p->paths[i-1] == NULL) {
      return 1;
    }
  }
  // successfully allocated memory for all args
  return 0;
}

void print_paths(struct path *p) {
  int i;
  for(i = 0; i < p->count; i++) {
    printf("path[%d]: %s\n", i, p->paths[i]);
  }
}

int main() {
  //the one and only error message:

  //write(STDERR_FILENO, error_message, strlen(error_message));

  struct path p = {.count = 0, .paths = NULL};

  const char *greeting = "whoosh> ";
  char *init_paths[1];
  init_paths[0] = "/bin";
  update_path(&p, init_paths, 1);

  int argv;
  char input[MAX_LEN];
  while(1) {

    printf("%s", greeting); 
    fgets(input, MAX_LEN, stdin);

    char *args[MAX_LEN-2];


    //  PRINT ERROR MESSAGE AND EXIT:
    //---------------------------------------
    //An incorrect number of command line arguments to your shell program.

    //    PRINT ERROR MESSAGE AND CONTINUE:
    //---------------------------------------
    //A command does not exist or cannot be executed.
    // if not found on path or exec returns...
    //A very long command line (over 128 bytes).
    if (strlen(input) == MAX_LEN-1 && input[strlen(input)-2] != '\n') {
      err();
    }

  
    //  NOT ERRORS:
    //---------------------------------------
    //An empty command line.
    if(strlen(input) == 1) {
      continue;
    }
  
    //Multiple white spaces on a command line.
    // WHAT ABOUT TABS?
    int i;
    for(i = 0; i < strlen(input) - 1; i++) {
      if(input[i] != ' ') {
	break;
      }
    }
    if(i == strlen(input)-1) {
      continue;
    }

  
    // check all error states above
    // (MAX/2 + 1 since, if every other letter is a token and there are 128 allowed, then there can only be 64)
    // strtok -> *char[MAX_LENGTH/2]
    args[0] = strtok(input, " ");
    if(args[0] == NULL) {
      continue;
    }

    argv = 1;
    while((args[argv] = strtok(NULL, " ")) != NULL) {
      argv++;
    }

    // now argv == the # of args



    //So far, you have added your own exit built-in command. Most Unix shells have many others such as cd , echo , pwd , etc. In this project, you should implement exit, cd, pwd, and path.

    //The formats for exit, cd, and pwd are:

    //[optionalSpace]exit[optionalSpace]
    if(argv == 1 && strcmp(args[0], "exit\n") == 0) {
      exit(0);
    } else if(strcmp(args[0], "exit") == 0) {
      err();
      exit(1);
    }


    //[optionalSpace]pwd[optionalSpace]
    else if(argv == 1 && strcmp(args[0], "pwd\n") == 0) {
      char *cwd;
      cwd = getcwd(NULL, 0);
      printf("%s\n", cwd);
      free(cwd);
    } else if (strcmp(args[0], "pwd") == 0) {
      err();
      exit(1);
    }


    else if ((argv == 1 && strcmp(args[0], "cd\n") == 0) || 
	     (argv > 1 && strcmp(args[0], "cd") == 0)) {
      if(argv > 2) {
	err();
	exit(1);
      } else {
	char *dir;
	int res;

	//[optionalSpace]cd[optionalSpace]
	if(argv == 1) {
	  dir = getenv("HOME");
	  if(dir == NULL) {
	    err();
	    exit(1);
	  }
	} else {
	  //[optionalSpace]cd[oneOrMoreSpace]dir[optionalSpace]
	  dir = args[1];
	}

	res = chdir(dir);
	if(res != 0) {
	  err();
	  exit(1);
	} else {
	  continue;
	}
      }
    }
    //[optionalSpace]path[oneOrMoreSpace]dir[optionalSpace] (and possibly
    else if ((argv == 1 && strcmp(args[0], "path\n") == 0) || 
	     (argv > 1 && strcmp(args[0], "path") == 0)) {
      update_path(&p, args, argv);
      //print_paths(&p);
    }

    // search path for arg
    else {
      //For the following situation, you should print the error message to stderr and continue processing:
      //  - A command does not exist or cannot be executed.



    }
  }


  // clean up
  clear_paths(&p);

  exit(0);
}

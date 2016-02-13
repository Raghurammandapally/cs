#include <sys/types.h>
#include <dirent.h>
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

void writeout(char *str) {
  write(STDOUT_FILENO, str, strlen(str));
}

void err() {  
  write(STDERR_FILENO, error_message, strlen(error_message)); 
}

void clear_paths(struct path *p) {
  int i;
  //argc/argv are passed directly from the cli, so they include:
  // argv[0]: path
  // argv[1]: (first dir)
  // ...
  // argv[argc-1]: (last dir)

  // free all old pointers
  for(i = 0; i < p->count; i++) {
    free(p->paths[i]);
  }
  if(p->paths != NULL) {
    free(p->paths);
    p->paths = NULL;
  }
}

int update_path(struct path *p, char **argv, int argc) {
  clear_paths(p);

  int i;
  // set count var before potentially returning
  p->count = argc-1;

  if(argc == 1) {
    //no need to allocate space
    return 0;
  }

  // add all new *chars
  p->paths = malloc((argc-1)*sizeof(char*));
  if(p->paths == NULL) {
    return 1;
  }
  for(i = 1; i < argc; i++) {
    p->paths[i-1] = strdup(argv[i]);
    if(p->paths[i-1] == NULL) {
      return 1;
    }
  }
  // successfully allocated memory for all args
  return 0;
}


int main() {
  //the one and only error message:

  //write(STDERR_FILENO, error_message, strlen(error_message));

  struct path p = {.count = 0, .paths = NULL};

  char *greeting = "whoosh> ";
  char *init_paths[2];
  // has to start at 1 because update_paths starts there
  init_paths[1] = "/bin";
  // have to pass 1+1 == 2 since we calculate argc-1 in update_path
  update_path(&p, init_paths, 2);

  int argc;
  char input[MAX_LEN];
  while(1) {

    writeout(greeting);
    //printf("%s", greeting); 
    fgets(input, MAX_LEN, stdin);

    char *argv[MAX_LEN-2];


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
      continue;
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
    argv[0] = strtok(input, " ");
    if(argv[0] == NULL) {
      continue;
    }

    argc = 1;
    while((argv[argc] = strtok(NULL, " ")) != NULL) {
      argc++;
    }

    //So far, you have added your own exit built-in command. Most Unix shells have many others such as cd , echo , pwd , etc. In this project, you should implement exit, cd, pwd, and path.

    //The formats for exit, cd, and pwd are:

    //[optionalSpace]exit[optionalSpace]
    if(argc == 1 && strcmp(argv[0], "exit\n") == 0) {
      exit(0);
    } else if(strcmp(argv[0], "exit") == 0) {
      err();
      exit(1);
    }


    //[optionalSpace]pwd[optionalSpace]
    else if(argc == 1 && strcmp(argv[0], "pwd\n") == 0) {
      char *cwd;
      cwd = getcwd(NULL, 0);
      writeout(cwd);
      writeout("\n");
      //printf("%s\n", cwd);
      free(cwd);
    } else if (strcmp(argv[0], "pwd") == 0) {
      err();
      exit(1);
    }


    else if ((argc == 1 && strcmp(argv[0], "cd\n") == 0) || 
	     (argc > 1 && strcmp(argv[0], "cd") == 0)) {
      if(argc > 2) {
	err();
	exit(1);
      } else {
	char *dir;
	int res;

	//[optionalSpace]cd[optionalSpace]
	if(argc == 1) {
	  dir = getenv("HOME");
	  if(dir == NULL) {
	    err();
	    exit(1);
	  }
	} else {
	  //[optionalSpace]cd[oneOrMoreSpace]dir[optionalSpace]
	  dir = argv[1];
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
    else if ((argc == 1 && strcmp(argv[0], "path\n") == 0) || 
	     (argc > 1 && strcmp(argv[0], "path") == 0)) {
      update_path(&p, argv, argc);
      //print_paths(&p);
    }

    // search path for arg
    else {
      //For the following situation, you should print the error message to stderr and continue processing:
      //  - A command does not exist or cannot be executed.

      //if(argv[0][strlen(argv[0])-1] == '\n') {
      argv[argc-1][strlen(argv[argc-1])-1] = '\0';
      //}

      int j;
      struct dirent *de;
      int found = 0;
      for(j = 0; j < p.count; j++) {
	DIR *dir = opendir(p.paths[j]);
	if(dir == NULL) {
	  continue;
	} 
	// statically allocated; no need to free()
	while( (de = readdir(dir)) != NULL) {
	  if( strcmp(argv[0], de->d_name) == 0) {
	    // we have found our (potential) executable
	    found = 1;
	    int rc = fork();
	    if(rc == 0) { // child process
	      char *my_args[argc+1];

	      // 1 for '/' between and 1 for \0
	      my_args[0] = malloc(sizeof(char)*( strlen(p.paths[j]) + strlen(argv[0]) + 2));

	      strcpy(my_args[0], p.paths[j]);
	      strcat(my_args[0], "/");
	      strcat(my_args[0], argv[0]);

	      my_args[argc] = NULL;

	      int i;
	      for(i = 1; i < argc; i++) {
		my_args[i] = argv[i];
	      }
	      my_args[argc] = NULL;

	      //execv: must be passed a **char with the last == NULL
	      execv(my_args[0], my_args);
	      //there has been an error in the execv call if this line executes
	      err();
	      break;
	    } else if (rc > 0) { // parent process

	    } else { // error with execv
	      err();
	      exit(1);
	    }
	  }
	}
	//break out of for loop over paths
	if(found) {
	  break;
	}
      }
    }
  }

  // clean up
  clear_paths(&p);

  exit(0);
}

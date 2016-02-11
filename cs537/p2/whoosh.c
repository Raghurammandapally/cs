#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
// 128 for chars, 1 for \n and 1 for \0
#define MAX_LEN 130

const char error_message[30] = "An error has occurred\n";

void err() {  
  write(STDERR_FILENO, error_message, strlen(error_message)); 
}

int main() {
  //the one and only error message:

  //write(STDERR_FILENO, error_message, strlen(error_message));

  const char *greeting = "whoosh> ";

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
    printf("ack!\n");
    exit(0);
  } else if(strcmp(args[0], "exit" == 0)) {
    err();
    exit(1);
  }


    //[optionalSpace]pwd[optionalSpace]
  else if(argv == 1 && strcmp(args[0], "pwd\n") == 0) {
      char *cwd;
      cwd = get_current_dir_name()
	print("%s\n", cwd);
      free(cwd);
  } else if (strcmp(args[0], "pwd") == 0) {
      err();
      exit(1);
  }

    break;
    /*

    else if (strcmp(args[0], "cd") == 0) {
      if(argv > 2) {
    error....
      exit(1);
      } else {
	char *dir;
	int res;

	//[optionalSpace]cd[optionalSpace]
	if(argv == 1) {
	  dir = getenv("HOME");
	  if(dir == NULL) //error...

	    //[optionalSpace]cd[oneOrMoreSpace]dir[optionalSpace]
	    } else {
	  dir = args[1];
	}

	res = chdir(home);
	if(res != 0) //error...
	else {
      continue
	}
      }
    }


    //[optionalSpace]path[oneOrMoreSpace]dir[optionalSpace] (and possibly
    else if (strcmp(args[0], "path") == 0) {
  */  








  }
  exit(0);

}

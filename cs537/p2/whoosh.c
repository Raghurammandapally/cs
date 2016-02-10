#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
// 128 for chars, 1 for \n and 1 for \0
#define MAX_LEN 130



int main() {
  //the one and only error message:
  const char error_message[30] = "An error has occurred\n";
  //write(STDERR_FILENO, error_message, strlen(error_message));

  const char *greeting = "whoosh> ";

  char input[MAX_LEN];

  printf("%s", greeting); 
  fgets(input, MAX_LEN, stdin);

  char *args[MAX_LEN-2];
  printf("you wrote: %s\n", input);

  args[0] = strtok(input, " ");
  printf("first arg: %s\n", args[0]);

  exit(0);

}

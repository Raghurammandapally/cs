#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>


int main() {
  const char error_message[30] = "An error has occurred\n";
  //write(STDERR_FILENO, error_message, strlen(error_message));
  const char *greeting = "whoosh> ";
write(STDERR_FILENO, error_message, strlen(error_message));
  printf("%s", greeting); 
  exit(0);

}

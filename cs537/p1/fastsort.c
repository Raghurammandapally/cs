#include <stdio.h>
#include <stdlib.h>

void sort(char *input, int key);

int main(int argc, char *argv[]) {
  int key;
  char *input_file;

  if(argc == 2) {
    key = 1;
    input_file = argv[1];
  } else if (argc == 3) {
    key = atoi(argv[1]);
    if(key >= 0) {
      fprintf(stderr, "Error: Bad command line parameters\n");
      return 1;
    }
    // flip sign on passed arg
    key *= -1;

    input_file = argv[2];
  } else {
    fprintf(stderr, "Error: Bad command line parameters\n");
    return 1;
  }
  //printf("there were %d args.\n\
        argv[0] was %s\n\
        argv[1] was %s\n\
        argv[2] was %s\n", argc, argv[0], argv[1], argv[2]);
  sort(input_file, key);
  return 0;
}

void sort(char *input_file, int key) {
  FILE *fp = fopen(input_file, "r");
  if(fp == NULL) {
    fprintf(stderr, "Error: Cannot open file %s\n", input_file);
    exit(1);
  }

  char *line;
  line = fgets(line, 128, fp);
  printf("line: %s\n", line);
  

  fclose(fp);
}

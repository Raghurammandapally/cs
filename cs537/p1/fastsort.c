#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
// 128 chars + 1 for \n
#define MAX_LEN 129


void sort(char *input, int key);

struct line {
  char string[MAX_LEN + 1];
};

typedef struct line *Line;

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
  sort(input_file, key);
  return 0;
}

void sort(char *input_file, int key) {
  FILE *fp = fopen(input_file, "r");
  char *line;
  char str_buf[MAX_LEN + 1];
  int linecount = 0;
  int i;
  Line *lines;

  if(fp == NULL) {
    fprintf(stderr, "Error: Cannot open file %s\n", input_file);
    exit(1);
  }

  while(fgets(str_buf, sizeof str_buf, fp) != NULL) {
    if(strlen(str_buf) == MAX_LEN && str_buf[strlen(str_buf)-1] != '\n') {
      fprintf(stderr, "Line too long\n");
      exit(1);
    } else {
      //valid line
      linecount++;
    }
  }

  // go back to beginning of file
  rewind(fp);

  lines = malloc(sizeof(Line) * linecount);
  printf("sizeof struct line *: %d\n", sizeof(Line));
  printf("sizeof lines: %d\n", sizeof(lines));
  printf("usable space: %d\n", malloc_usable_size(lines));

  for(i = 0; i < linecount; i++) {
    //allocate a new line struct
    lines[i] = (Line) malloc(sizeof(struct line));
    fgets(lines[i]->string, sizeof(lines[i]->string), fp);
  }

  for(i = 0; i < linecount; i++) {
    printf("%s\n", lines[i]->string);
    //free(lines[i]);
  }

  free(lines);

  fclose(fp);
}

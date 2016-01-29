#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <malloc.h>
// 128 chars + 1 for \n
#define MAX_LEN 129


void sort(char *input, int key);
int compar(const void *p1, const void *p2);

struct line {
  char string[MAX_LEN + 1];
  char token[MAX_LEN + 1];
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

int compar(const void *p1, const void *p2) {
  Line *l1;
  Line *l2;
  l1 = (Line *) p1;
  l2 = (Line *) p2;
  return strcmp((*l1)->token, (*l2)->token);
}

void sort(char *input_file, int key) {
  FILE *fp = fopen(input_file, "r");
  char *line;
  char str_buf[MAX_LEN + 1];
  int linecount = 0;
  int i;
  int token_count;
  Line *lines;
  char *tok;
  char *tok_old;

  // check that the file exists
  if(fp == NULL) {
    fprintf(stderr, "Error: Cannot open file %s\n", input_file);
    exit(1);
  }

  // count the number of lines in the file for memory allocation
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

  // allocate space for Lines
  lines = malloc(sizeof(Line) * linecount);

  for(i = 0; i < linecount; i++) {
    //allocate a new line struct
    lines[i] = (Line) malloc(sizeof(struct line));
    fgets(lines[i]->string, sizeof(lines[i]->string), fp);
    strcpy(lines[i]->token, lines[i]->string);

    //char *strtok(char *str, const char *delim);

    //DESCRIPTION
    //The strtok() function parses a string into a sequence of tokens.  On the first call to strtok() the
    //string to be parsed should be specified in str.  In each subsequent call that should parse the same
    //string, str should be NULL.
    token_count = 1;
    tok = strtok(lines[i]->token, " ");
    if(tok == NULL) {
      strcpy(lines[i]->token, "\n\0");
      break;
    } else {
      // save off in case there is only 1 token on the line
      tok_old = tok;
    }

    while(token_count < key) {
      tok = strtok(NULL, " ");
      if(tok == NULL) {
        tok = tok_old;
        break;
      }
      tok_old = tok;
      token_count++;
    }

    strcpy(lines[i]->token, tok);
  }

  //The  qsort() function sorts an array with nmemb elements of size size.  The base argument points to
  //  the start of the array.
  //
  //void qsort(void *base, size_t nmemb, size_t size,
  //  int(*compar)(const void *, const void *));
  //
  qsort(
      lines, linecount, sizeof(Line), compar
      );

  // check the sort
  for(i = 0; i < linecount; i++) {
    printf("%s", lines[i]->string);
  }




  // CLEANUP
  // ---------------
  //free all memory
  for(i = 0; i < linecount; i++) {
    free(lines[i]);
  }

  free(lines);

  //close the file
  fclose(fp);
}

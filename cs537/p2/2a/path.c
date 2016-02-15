struct path {
  char **paths;
  int count;
};

void print_paths(struct path *p) {
  int i;
  for(i = 0; i < p->count; i++) {
    printf("path[%d]: %s\n", i, p->paths[i]);
  }
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

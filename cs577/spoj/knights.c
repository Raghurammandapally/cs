#include <stdio.h>
#include <limits.h>

//prototypes
int calc_moves();
int get_row(int idx);
int get_col(int idx);
int convert_col_row(int col, int row);

//fn defs
int calc_moves(int start, int end) {
  int moves[64] = {0};
  int k;
  for(k = 0; k < 64; k++) {
    moves[k] = INT_MAX;
  }

  int queue[64];
  int front = 0;
  int len = 0;

  moves[start] = 0;

  int i;
  while(start != end) {
    int start_col = get_col(start);
    int start_row = get_row(start);

    //check all 8 possible moves from start
    //if moves[start]+1 < moves[next], set moves[next]
    int dcol[8] = {1, 2,  2,  1, -1, -2, -2, -1};
    int drow[8] = {2, 1, -1, -2, -2, -1,  1,  2};

    for(i = 0; i < 8; i++) {
      int next_col = start_col + dcol[i];
      int next_row = start_row + drow[i];

      if(next_col <= 7 && next_col >= 0 && next_row <= 7 && next_row >= 0) {
        int next_idx = convert_col_row(next_col, next_row);
        if(moves[next_idx] == INT_MAX) { 
          moves[next_idx] = moves[start] + 1;
          queue[len] = next_idx;
          len++;
        }
      }
    }

    start = queue[front];
    front++;
  }
  return moves[end];
}

int main() {
  char start[2];
  char end[2];


  int T;
  scanf("%d", &T);

  int start_row;
  int start_col;
  int end_row;
  int end_col;

  int i;
  for(i = 0; i < T; i++) {
    scanf("%s", start);
    scanf("%s", end);

    start_col = start[0] - 'a';
    start_row = start[1] - '1';

    end_col = end[0] - 'a';
    end_row = end[1] - '1';

    printf("%d\n", calc_moves(
          convert_col_row(start_col, start_row), 
          convert_col_row(end_col, end_row)));
  }

  return 0;
}

int convert_col_row(int col, int row) {
  return (col * 8) + row;
}

int get_row(int idx) {
  return idx % 8;
}

int get_col(int idx) {
  return idx/8;
}


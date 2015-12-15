#include <stdio.h>
#include <stdio.h>

void set(int* A, size_t len, int i, int j, char new) {
  A[i*len + j] = new;
}

char get(int* A, size_t len, int i, int j) {
  return A[i*len + j];
}

int main() {
  int LEN = 6;
  int dp[LEN*LEN];

  char blanks[LEN];

  int i;
  int j;

  //initialize blanks
  for(i = 0; i < LEN; i++) {
    blanks[i] = ' ';
  }
  //blanks[4] = '[';
  //blanks[6] = '[';
  blanks[2] = '[';
  blanks[4] = '[';


  //initialize dp
  for(i = 0; i < LEN; i++) {
    for(j = 0; j < LEN; j++) {
      if(i == j) {
        set(dp, LEN, i, j, 0);
      } else {
        set(dp, LEN, i, j, -1);
      }
    }
  }

  opt(dp, blanks, LEN, 0, LEN-1);

  for(i = 0; i < LEN; i++) {
    for(j = 0; j < LEN; j++) {
      printf("\t%d ", get(dp, LEN, i, j));
    } 
    printf("\n");
  }

  return 0;
}

int opt(int* dp, char* blanks, int LEN, int i, int j) {
  if(get(dp, LEN, i, j) >= 0) {
    return get(dp, LEN, i, j);
  } else {
    int max;
    int temp;
    int k;
    max = 0;

    if(blanks[j] == '[') {
      set(dp, LEN, i, j, 0);
      return 0;
    }
    if((i >= j)) {
      set(dp, LEN, i, j, 0);
      return 0;
    } else if(j-i == 1) {
      set(dp, LEN, i, j, 1);
      return 1;
    } else {
      for(k = i + 1; k < j; k += 2) {
        max += opt(dp, blanks, LEN, i, k) * opt(dp, blanks, LEN, k+1, j);
        //if(temp > max) {
          //max = temp;
        //}
      }
      max += opt(dp, blanks, LEN, i + 1, j - 1);
      //if(temp > max) {
        //max = temp;
      //}
      set(dp, LEN, i, j, max); 
      return max;
    }
  }
}



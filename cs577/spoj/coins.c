#include <stdio.h>
int main() {
  int const SIZE = 1000000;
  char const BLANK = ' ';

  char dp[SIZE];
  int K;
  int L;
  int m;
  int N;

  scanf("%d", &K);
  scanf("%d", &L);
  scanf("%d", &m);

  int i;
  for(i = 0; i < SIZE; i++) {
    dp[i] = BLANK;
  }

  dp[0] = 'A';
  dp[K-1] = 'A';
  dp[L-1] = 'A';

  for(i = 1; i < SIZE; i++) {
    if(dp[i] == BLANK) {
      if( dp[i-1] == 'A' &&
          ((i-K < 0) || dp[i-K] == 'A') &&
          ((i-L < 0) || dp[i-L] == 'A')) {
        dp[i] = 'B';
      } else {
        dp[i] = 'A';
      }
    }
  }

  for(i = 0; i < m; i++) {
    scanf("%d", &N);
    printf("%c", dp[N-1]);
  }
  //printf("\n");

  return 0;
}

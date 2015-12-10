#include <stdio.h>
#include <limits.h>
#include <string.h>

//typedefs
typedef unsigned int uint;

//prototypes
void merge_sort(uint A[], uint size);
void merge_sort_recurse(uint A[], uint lo, uint hi);
void merge(uint A[], uint lo, uint m, uint hi);

//functions
void merge_sort(uint A[], uint size) {
  merge_sort_recurse(A, 0, size);
}

/*
Input: A[0 · · · n − 1], an array of length n
Output: B, a sorted copy of A
1: procedure Merge-Sort(A)
2: if n = 1 then
3:    return A
4: else
5:    m ← n/2
6:    L ← Merge-Sort(A[0 · · · (m − 1)])
7:    R ← Merge-Sort(A[m · · · (n − 1)])
8:    return Merge(L, R)
*/
void merge_sort_recurse(uint A[], uint lo, uint hi) {
  uint len = hi - lo;
  if(len <= 1) {
    return;
  } else {
    uint m = lo + (len/2);
    merge_sort_recurse(A, lo, m);
    merge_sort_recurse(A, m, hi);
    merge(A, lo, m, hi); 
  }
}

/*
Input: L[0 · · · (n L − 1)], R[0 · · · (n R − 1)], arrays of size n L and n R , respectively
Output: M , the concatenation of L and R, sorted
1: procedure Merge(L, R)
2: M ← array indexed by 0 · · · (n_L + n_R − 1)
3: l ← 0
4: r ← 0
5: m ← 0
6: while l < n_L and r < n_R do
7:    if L[l] ≤ R[r] then
8:      M [m] ← L[l]
9:      l ← l+1
10:   else
11:     M [m] ← R[r]
12:     r ← r +1
13:   m ← m +1
14: while < n L do
15:   M [m] ← L[l]
16:   l ← l+1
17:   m ← m +1
18: while r < n R do
19:   M [m] ← R[r]
20:   r ← r +1
21:   m ← m +1
22: return M
*/ 
void merge(uint A[], uint lo, uint m, uint hi) {
  uint len = hi - lo;
  uint B[len];

  uint l = lo;
  uint r = m;

  int i;
  for(i = 0; i < len; i++) {
    if(r == hi) {
      memcpy(B+i, A+l, (len-i)*sizeof(uint));
      break;
    } else if(l == m) {
      memcpy(B+i, A+r, (len-i)*sizeof(uint));
      break;
    } else if(A[l] < A[r]) {
      B[i] = A[l];
      l++;
    } else {
      B[i] = A[r];
      r++;
    }
  }

  memcpy(A+lo, B, len*sizeof(int));
}

int main() {
  uint A[100000];

  int len = 0;
  int ret;
  while(scanf("%u", &A[len]) >= 0) {
    len++;
  }

  int i;
  merge_sort(A, len);
  for(i = 0; i < len; i++) {
    printf("%u ", A[i]);
  }
  
  return 0;
}

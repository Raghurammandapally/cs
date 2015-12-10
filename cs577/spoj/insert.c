#include <stdio.h>
#include <string.h>

//typedefs
typedef unsigned int uint;

//fn prototypes
uint count_inversions(uint *A, size_t size);
uint count_inversions_recurse(uint *A, uint lo, uint hi);
uint count_cross(uint *A, uint lo, uint m, uint hi);
void merge(uint *A, uint lo, uint m, uint hi);

//functions
uint count_inversions(uint *A, size_t size) {
  return count_inversions_recurse(A, 0, size);
}

/*
Input: A[0 · · · n − 1], an array of length n
Output: (c, B), where c is the number of inversions in A and B is a sorted copy of A
1:  procedure Count-And-Sort(A)
2:  if n = 1 then
3:    return (0, A)
4:  else
5:    m ← n/2
6:    (c L , L) ← Count-And-Sort(A[0 · · · (m − 1)])
7:    (c R , R) ← Count-And-Sort(A[m · · · (n − 1)])
8:    c_cross ← Count-Cross(L, R)
9:    c ← c L + c R + c cross
10:   B ← Merge(L, R)
11:   return (c, B)
*/
uint count_inversions_recurse(uint *A, uint lo, uint hi) {
  int len = hi - lo;
  if(len == 1) {
    return 0;
  } else {
    uint m = lo + (len / 2);
    uint c_L = count_inversions_recurse(A, lo, m);
    uint c_R = count_inversions_recurse(A, m, hi);
    uint c_Cross = count_cross(A, lo, m, hi);
    uint c = c_L + c_R + c_Cross;
    merge(A, lo, m, hi);
    return c;
  }
}

/*
Input: L[0 · · · (n L − 1)], R[0 · · · (n R − 1)], sorted arrays of size n L and n R , respectively
Output: c, the number of pairs (i, j) with L[i] > R[j]
1: procedure Count-Cross(L, R)
2:  l ← 0
3:  r ← 0
4:  c ← 0
5:  while l < n_L and r < n_R do
6:    if L[l] ≤ R[r] then
7:      l ← l + 1
8:    else
9:      c ← c + n_L − l
10:     r ← r + 1
11: return c
*/
uint count_cross(uint *A, uint lo, uint m, uint hi) {
  uint len = hi - lo;
  uint l = 0;
  uint r = 0;
  uint nL = m - lo;
  uint nR = hi - m;
  uint c = 0;
  while(l < nL && r < nR) {
    if(A[l + lo] <= A[r + m]) {
      l++;
    } else {
      c += nL - l;
      r++;
    }
  }
  return c;
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

  memcpy(A+lo, B, len*sizeof(uint));
}



int main(int argc, char *argv[]) {
	uint len;
  uint T;

  scanf("%u", &T);

  int i;
  int j;
  for(i = 0; i < T; i++) {
    scanf("%u", &len);

    unsigned int A[len];

    for(j = 0; j < len; j++) {
      scanf("%u", &A[j]);
    }

    printf("%u\n", count_inversions(A, len));
  }

	return 0;
}

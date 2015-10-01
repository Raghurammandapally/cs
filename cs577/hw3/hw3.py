import math

#for comparison of (in)equality with reals
tol = 0.000001

def weightedMedian(A,W):
  return weightedMedianRec(A,W,0,len(A)-1,0.5)

def weightedMedianRec(A,W,lo,hi,target):
  if(lo == hi):
    return A[lo]
  else:
    pivot = lowMedian(A,lo,hi)
    pivot = partition(A,W,lo,hi,pivot)
    total = sumWeights(A,W,lo,pivot)
    #total > target, recurse on L
    if total + tol > target:
      return weightedMedianRec(A,W,lo,pivot - 1,target)
    #total <= target
    else:
      #total + W[pivot] >= target, return pivot value
      if tol  >= target - (W[pivot] + total):
        return A[pivot]
      #total + W[pivot] < target, recurse on R
      else:
        return weightedMedianRec(A,W,pivot + 1,hi,target - total - W[pivot])

def sumWeights(A,W,lo,pivot):
  total = 0
  for i in range(lo,pivot):
    total += W[i]
  return total

#hacked implementation of O(n log n) median implementation
# NOTE we are suggesting that this be replaced with the worst-case O(n) 
# median-of-medians algorithm discussed in class
def lowMedian(A,lo,hi):
  B = A[lo:hi+1]
  C = B[:]
  B.sort()
  # location of low median in 
  k = int(math.ceil(len(B)/2.0)) - 1
  #return A.index(B[k])
  return lo + C.index(B[k])

#partition s.t. A[i] >= A[pivot] for all i >= pivot
# and A[i] < A[pivot] for all i < pivot
# see Cormen, et al. p. 171
def partition(A,W,lo,hi,pivot):
  pivotVal = A[pivot]
  swap(A,hi,pivot)
  swap(W,hi,pivot)
  i = lo 
  for j in range(lo,hi):
    if( A[j] < pivotVal ):
      swap(A,i,j)
      swap(W,i,j)
      i += 1
  swap(A,hi,i)
  swap(W,hi,i)
  return i

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

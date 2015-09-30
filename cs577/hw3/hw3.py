import math
import pdb
import random
from generateInput import printArr,generateInput
from statistics import median_high

tol = 0.000001

def weightedMedian(A,W):
  return weightedMedianRec(A,W,0,len(A)-1,0.5)

def weightedMedianRec(A,W,lo,hi,target):
  try:
    #pdb.set_trace()
    if(lo == hi):
      return A[lo]
    else:
      pivot = lowMedian(A,lo,hi)
      pivot = partition(A,W,lo,hi,pivot)
      total = sumWeights(A,W,lo,pivot)
      #else:
      if total + tol > target:
        return weightedMedianRec(A,W,lo,pivot - 1,target)
      #if total < target:
      #if total - target < tol:
      else:
        #if total + W[pivot] >= target - tol
        if tol  >= target - (W[pivot] + total):
          return A[pivot]
        else:
          return weightedMedianRec(A,W,pivot + 1,hi,target - total - W[pivot])
  except RuntimeError:
    print("RUNTIME! Dumping A and W...\n%s\n%s" % (str(A),str(W)))


def sumWeights(A,W,lo,pivot):
  total = 0
  for i in range(lo,pivot):
    total += W[i]
  return total

# returns index of the low median of A on [lo,hi]
def lowMedian(A,lo,hi):
  # [ 1 4 3 2 ] [ 1 4 3 2 5 ]
  #   0 1 2 3     0 1 2 3 4
  #     ^             ^
  # int(math.floor((hi - lo + 1)/2.0)) + lo
  B = A[lo:hi+1]
  C = B[:]
  B.sort()
  # location of low median in 
  k = int(math.ceil(len(B)/2.0)) - 1
  #return A.index(B[k])
  return lo + C.index(B[k])

#partition s.t. A[i] >= A[pivot] for all i >= pivot
# and A[i] < A[pivot] for all i < pivot
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

def bruteForceWM(a,w,target=0.5):
  #pdb.set_trace()
  [sortA,sortW] = zip(*sorted(zip(a,w)))
  total = 0
  ptr = 0
  #while(total < target):
  
  while(tol < target - total):
    total += sortW[ptr]
    ptr += 1
  return sortA[ptr - 1]


#######################################
#         TEST CASES                  #
#######################################

samples = True

if samples:
  N = 1000
  for i in range(0,N):
    [a,w] = generateInput(100)
    a_orig = a[:]
    w_orig = w[:]
    wm = weightedMedian(a,w)
    bwm = bruteForceWM(a,w)
    if wm != bwm:
      print("ACK! Brute: %d; Yours: %d; Dumping A and W...\n%s\n%s" % (bwm,wm,str(a_orig),str(w_orig)))
  print("done testing!")
else:
  #example given on pset
  A = [40,-5,4,0,2.5,6,-2]
  W = [.25,.1,.05,.18,.15,.2,.07]
  print(bruteForceWM(A,W))
  print(weightedMedian(A,W))
  print(weightedMedian(A,W))

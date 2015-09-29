import math
import pdb
import random
from generateInput import printArr,generateInput
from statistics import median_high

tol = 0.000001

def weightedMedian(A,W):
  return weightedMedianRec(A,W,0,len(A)-1,0.5)

def weightedMedianRec(A,W,lo,hi,target):
  #pdb.set_trace()
  if(lo == hi):
    return A[lo]
  else:
    n = hi - lo + 1
    #k is median
    k = int(math.floor(n/2))+1
    pivot = select(A,W,lo,hi,k)
    medSum = 0
    restSum = 0
    for i in range(lo,pivot+1):
      if(A[i] == A[pivot]):
        medSum += W[i]
      else:
        restSum += W[i]
    totalSum = medSum + restSum
    #pdb.set_trace()
    if(totalSum < target):
      return weightedMedianRec(A,W,pivot+1,hi,target - totalSum)
    #totSum >= target
    else:
      if(totalSum - medSum < target):
        #print("FOUND IT %d" % A[pivot])
        return A[pivot]
      else:
        if(lo + 1 == hi):
          return A[lo]
        else:
          return weightedMedianRec(A,W,lo,pivot,target)

def median(A):
  n = len(A)
  k = int(math.floor(n/2))+1
  return A[select(A,0,n - 1,k)]

def select(A,W,left,right,n):
  # n must be on [1, right - left + 1]
  if(left == right):
    return left
  else:
    pivotIndex = pivot(A,W,left,right)
    pivotIndex = partition(A,W,left,right,pivotIndex)

    # A: [3,1,2,4,9,5,7,6]
    #           ^ pivotIndex (3)
    # i:  0   2   4   6
    # if we are looking for the 4th (pivotIndex + 1) smallest elt, then return pivotIndex
    # if we are looking for the 7th smallest elt, then recurse on list with [pivotIndex + 1, right], and looking for the n - (pivotIndex-left+1)th elt
    # 
    if(n == pivotIndex - left + 1):
      return pivotIndex
    elif(n < pivotIndex - left + 1):
      return select(A,W,left,pivotIndex - 1,n)
    else:
      return select(A,W,pivotIndex + 1,right,n - (pivotIndex - left + 1))

def pivot(A,W,left,right):
  for i in range(left,right,5):
    subR = i + 4
    if(subR > right):
      subR = right
    median5 = partition5(A,W,i,subR)
    swap(A,median5,left + int(math.floor((i - left)/5)))
    swap(W,median5,left + int(math.floor((i - left)/5)))
  newR = left + int(math.ceil((right-left+1)/5)) - 1
  newN = int(math.floor((newR - left + 1)/2)) + 1
  return select(A,W,left,newR,newN)
  
# our O(1) median finder, using insertion sort
# https://en.wikipedia.org/wiki/Insertion_sort#Algorithm
def partition5(A,W,left,right):
  for i in range(left + 1, right + 1):
    j = i
    while(j > left and A[j - 1] > A[j]):
      swap(A,j,j - 1)
      swap(W,j,j - 1)
      j -= 1
  return left + int(math.ceil((right - left) / 2))

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

def partition(A,W,lo,hi,pivot):
  pivotVal = A[pivot]
  swap(A,hi,pivot)
  swap(W,hi,pivot)
  i = lo 
  for j in range(lo,hi):
    if( A[j] <= pivotVal ):
      swap(A,i,j)
      swap(W,i,j)
      i += 1
  swap(A,hi,i)
  swap(W,hi,i)
  return i

def bruteForceWM(a,w):
  [sortA,sortW] = zip(*sorted(zip(a,w)))
  sum = 0
  ptr = 0
  while(sum<0.5):
    sum += sortW[ptr]
    ptr += 1
  return sortA[ptr - 1]

N = 100
for i in range(1,2*N+1):
  [A,W] = generateInput(i)
#A = [-65, -76, -91, -69, -87, -63, -93, -60, -52, -45, -44, -37, -39, -42, -34, -27, -19, -12, -11, 10, 14, 15, 16, 62, 79, 17, 25, 64, 38, 91, 65, 31, 52, 52, 63, 39, 78, 82, 33, 100, 80]
#W = [0.026, 0.01, 0.014, 0.047, 0.017, 0.034, 0.037, 0.013, 0.027, 0.024, 0.021, 0.039, 0.036, 0.039, 0.039, 0.013, 0.017, 0.047, 0.013, 0.008, 0.006, 0.044, 0.005, 0.04, 0.049, 0.041, 0.0, 0.038, 0.043, 0.026, 0.021, 0.003, 0.013, 0.028, 0.019, 0.008, 0.009, 0.022, 0.005, 0.023999999999999445, 0.035]
  print("A is of length %d. Sample: %s" % (len(A), str(A[0,min(len(A),5)])))

  w = weightedMedian(A,W)
  b = bruteForceWM(A,W)
  if(w != b):
    print("ERROR! our median algorithm found %d while theirs found %d. Dump of A:\n%s\n%s" % (w, b, str(A), str(W)))
  else:
    continue

print("done! ran through %d test cases." % N)
"""

#[-91, -68, -67, -62, -62, -60, -2, -18, 9, 25, 31, 51, 96, 56, 63, 60, 87, 81, 83, 78]
#[0.003, 0.076, 0.008, 0.053, 0.018, 0.086, 0.077, 0.073, 0.035, 0.076, 0.005, 0.061, 0.037, 0.07, 0.08, 0.091, 0.006000000000000002, 0.071, 0.008, 0.066]
"""




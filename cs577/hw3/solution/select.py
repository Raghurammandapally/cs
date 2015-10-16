import math
import pdb
import random
from generateInput import printArr,generateInput
from statistics import median_high

def median(A):
  n = len(A)
  k = int(math.floor(n/2))+1
  return A[select(A,0,n - 1,k)]

def select(A,left,right,n):
  # n must be on [1, right - left + 1]
  if(left == right):
    return left
  else:
    pivotIndex = pivot(A,left,right)
    pivotIndex = partition(A,left,right,pivotIndex)

    # A: [3,1,2,4,9,5,7,6]
    #           ^ pivotIndex (3)
    # i:  0   2   4   6
    # if we are looking for the 4th (pivotIndex + 1) smallest elt, then return pivotIndex
    # if we are looking for the 7th smallest elt, then recurse on list with [pivotIndex + 1, right], and looking for the n - (pivotIndex-left+1)th elt
    # 
    if(n == pivotIndex - left + 1):
      return pivotIndex
    elif(n < pivotIndex - left + 1):
      return select(A,left,pivotIndex - 1,n)
    else:
      return select(A,pivotIndex + 1,right,n - (pivotIndex - left + 1))

def pivot(A,left,right):
  for i in range(left,right,5):
    subR = i + 4
    if(subR > right):
      subR = right
    median5 = partition5(A,i,subR)
    swap(A,median5,left + int(math.floor((i - left)/5)))
  newR = left + int(math.ceil((right-left+1)/5)) - 1
  newN = int(math.floor((newR - left + 1)/2)) + 1
  return select(A,left,newR,newN)
  
# our O(1) median finder, using insertion sort
# https://en.wikipedia.org/wiki/Insertion_sort#Algorithm
def partition5(A,left,right):
  for i in range(left + 1, right + 1):
    j = i
    while(j > left and A[j - 1] > A[j]):
      swap(A,j,j - 1)
      j -= 1
  return left + int(math.ceil((right - left) / 2))

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

def partition(A,lo,hi,pivot):
  pivotVal = A[pivot]
  swap(A,hi,pivot)
  i = lo 
  for j in range(lo,hi):
    if( A[j] <= pivotVal ):
      swap(A,i,j)
      i += 1
  swap(A,hi,i)
  return i

N = 1000
for i in range(1,N+1):
  A = [random.randint(0,100) for j in range(0,i)]
  myMed = median(A)
  theirMed = median_high(A) 
  if(myMed != theirMed):
    print("ERROR! our median algorithm found %d while theirs found %d. Dump of A:\n%s" % (myMed, theirMed, str(A)))
  else:
    continue
print("done! ran through %d test cases." % N)


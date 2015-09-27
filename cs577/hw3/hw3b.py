import math
import pdb
import random
from generateInput import printArr,generateInput

def median(A):
  n = len(A)
  return A[select(A,0,n - 1,int(math.ceil(n/2)))]

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
    if(n == pivotIndex + 1):
      return pivotIndex
    elif(n < pivotIndex + 1):
      return select(A,left,pivotIndex - 1,n)
    # n > pivotIndex + 1
    else:
      return select(A,pivotIndex + 1,right,n - (pivotIndex - left + 1))

def pivot(A,left,right):
  for i in range(left,right,5):
    subR = i + 4
    if(subR > right):
      subR = right
    median5 = partition5(A,i,subR)
    swap(A,median5,int(math.floor(i/5)))
  newR = left + int(math.ceil((right-left)/5)-1)
  newN = (right - left)/10
  return select(A,left,newR,newN)
  
def partition5(A,left,right):
  for i in range(left + 1, right + 1):
    j = i
    while(j > left and A[j - 1] > A[j]):
      swap(A,j,j - 1)
      j -= 1
  return int(math.ceil((right - left) / 2))

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

def partition(A,lo,hi,pivot):
  pivot = A[pivot]
  i = lo 
  # j = [lo,hi-1]
  for j in range(lo,hi + 1):
    if( A[j] <= pivot ):
      swap(A,i,j)
      i += 1
  return i - 1

A = [2,5,6,7,3,4,9,1]
print(median(A))
A.sort()
print(A)



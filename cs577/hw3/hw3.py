import pdb
import math
import random

###############################################
#    MEDIAN OF MEDIANS FOR WEIGHTED MEDIAN    #
###############################################
#constants
W = 5
def median(a):
  return medianRec(a,0,len(a)-1,len(a))

def medianRec(a,left,right,n):
  pdb.set_trace()
  if(left==right):
    return a[left]
  while(left!=right):
    piv = pivot(a,left,right)
    a = partition(a,left,right,piv)
    if(n == piv):
      return a[n]
    elif(n < piv):
      right = piv- 1
      left = piv+ 1

def pivot(a,left,right):
  for i in range(left,right,W):
    subRight = i + (W-1)
    if(subRight > right):
      subRight = right
    medianW = myMedian(a,i,subRight)
    toSwap = int(math.floor(i/W))
    a[medianW],a[toSwap] = a[toSwap],a[medianW]
  nextR = left + int(math.ceil((right - left) / 5) - 1)
  nextN = int((right - left) / 10)
  return medianRec(a,left,nextR,nextN)

#see https://en.wikipedia.org/wiki/Quicksort#Lomuto_partition_scheme
#  modified to include a pivot parameter
def partition(a,left,right,pivot):
  i = left
  for j in range(left,right):
    if(a[j] <= pivot):
      a[i],a[j] = a[j],a[i]
      i += 1
  a[i],a[right] = a[right],a[i]
  return a

def myMedian(a,i,j):
  A = a[i:j+1]
  i = 0
  j = len(A)-1
  for i in range(1,j+1):
    j = i
    while((j > 0) and (A[j-1] > A[j])):
      A[j],A[j-1] = A[j-1],A[j]
      j = j - 1
  #even
  if(len(A)%2 == 0):
    return A[len(A)/2]
  #odd
  else:
    return A[(len(A)-1)/2]

print(median([1,2,3,4,5]))

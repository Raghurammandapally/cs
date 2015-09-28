import math
import pdb
import random
from generateInput import printArr,generateInput
from statistics import median_high

def brutePivot(A,left,right):
  B = A[left:right+1]
  print("array: \n*************************")
  print(B)
  Bblk = [B[i:i+5] for i in range(0,len(B),5)]
  print(Bblk)
  medians = [median_high(a) for a in Bblk]
  print(medians)
  med = median_high(medians)
  #print("-----------\n  brute force found these blocks: \n" + str(Bblk) + " + these medians: " + str(medians) + " + this mom: " + str(med))
  #print("brute over\n-----------")
  return B.index(med) + left

def median(A):
  n = len(A)
  k = int(math.floor(n/2))+1
  #print("we are finding the %dth largest num of A." % k)
  #print(A)
  B = A[:]
  B.sort()
  #print("sorted A: " + str(B))
  #print("actual median: " + str(median_high(A)))
  return A[select(A,0,n - 1,k)]

def select(A,left,right,n):
  #pdb.set_trace()
  # n must be on [1, right - left + 1]
  #print(A)
  #print("  selecting the %dth elt of [%d,%d]: %s" % (n, left, right, str(A[left:right+1])))
  if(left == right):
    return left
    #print("**************\nfound it!!!!! %d" % left)
  else:
    pivotIndex = brutePivot(A,left,right)
    print("i'm expecting a pivot of A[%d]=%d" % (pivotIndex,A[pivotIndex]))
    pivotIndex = pivot(A,left,right)
    #print("  pivot index: %d" % pivotIndex)
    #print("  pivot value: %d" % A[pivotIndex])
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
      #print("we are going to the left because we are looking for the %dth element and the partition index is at %d" % (n,pivotIndex))
      #print("recurse on [%d,%d]" % (left, pivotIndex - 1) )
      return select(A,left,pivotIndex - 1,n)
    # n > pivotIndex + 1
    else:
      #print("we are going to the right because we are looking for the %dth element and the partition index is at %d" % (n,pivotIndex))
      #print("recurse on [%d,%d]" % (pivotIndex + 1, right))
      return select(A,pivotIndex + 1,right,n - (pivotIndex - left + 1))

def pivot(A,left,right):
  #pdb.set_trace()
  for i in range(left,right,5):
    subR = i + 4
    if(subR > right):
      subR = right
    median5 = partition5(A,i,subR)
    swap(A,median5,int(math.floor(i/5)))
  newR = left + int(math.ceil((right-left+1)/5)) - 1
  newN = int(math.floor((newR - left + 1)/2)) + 1
  #pdb.set_trace()
  pdb.set_trace()
  return select(A,left,newR,newN)
  
def partition5(A,left,right):
  #pdb.set_trace()
  for i in range(left + 1, right + 1):
    j = i
    while(j > left and A[j - 1] > A[j]):
      swap(A,j,j - 1)
      j -= 1
  return left + int(math.ceil((right - left) / 2))

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

def partition(A,lo,hi,pivot):
  #print("partition begin on [%d,%d] with pivot A[%d]=%d\n---------------------" %(lo,hi,pivot,A[pivot]))
  #print(A)
  pivotVal = A[pivot]
  swap(A,hi,pivot)
  i = lo 
  # j = [lo,hi-1]
  for j in range(lo,hi):
    if( A[j] <= pivotVal ):
      swap(A,i,j)
      i += 1
  swap(A,hi,i)
  #print(A)
  #print("partitioned at A[%d]=%d" % (i, A[i]))
  #print("partition over\n---------------")
  return i

A = [10, 76, 18, 38, 2, 43, 25, 60, 86, 97, 19, 9, 28, 92, 91, 13, 6, 79, 0, 62, 26, 72, 94]
#A = [0, 1, 21, 12, 28, 74, 100, 14, 93, 2, 62, 83, 33, 3, 75, 15, 54, 14, 14, 76, 30, 22, 60, 81, 86, 26, 56, 49, 71, 48, 7, 6, 74, 95, 66, 6, 11, 8, 39, 17, 86, 1, 16, 18, 47, 28, 6, 31, 36, 18, 32, 12, 52, 87, 21, 80, 31, 83, 32, 54, 11, 16, 10, 76, 3, 33, 93, 11, 45, 33, 11, 98, 4, 49, 73, 37, 46, 23, 12, 33, 16, 86, 34, 87, 1, 99, 17, 54, 8, 80, 56, 99, 21, 30, 41, 28]


#for i in range(1,100):
  #A = [random.randint(0,100) for j in range(0,i)]
myMed = median(A)
theirMed = median_high(A) 
if(myMed != theirMed):
  print("ERROR!!!! my %d their %d \n%s" % (myMed, theirMed, str(A)))
else:
  print("woohoo (n=%d)" % len(A))


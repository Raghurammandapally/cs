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
    [p,q] = partition(A,W,lo,hi,pivot)
    totalL = sumWeights(A,W,lo,p)
    totalM = sumWeights(A,W,p,q+1)
    #total > target, recurse on L
    if totalL + tol > target:
      return weightedMedianRec(A,W,lo,p - 1,target)
    #total <= target
    else:
      #total + W[pivot] >= target, return pivot value
      if tol  >= target - (totalM + totalL):
        return A[p]
      #total + W[pivot] < target, recurse on R
      else:
        return weightedMedianRec(A,W,q + 1,hi,target - totalL - totalM)

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

def partition(a,w,lo,hi,pivot):                                                         
  pivotValue = a[pivot]                                                         
  i = lo                                                                        
  j = lo                                                                        
  n = hi                                                                        
  while(j <= n):                                                                
    if(a[j] < pivotValue):                                                      
      swap(a,i,j)                                                               
      swap(w,i,j)
      i += 1                                                                    
      j += 1                                                                    
    elif a[j] > pivotValue:                                                     
      swap(a,j,n)                                                               
      swap(w,j,n)                                                               
      n -= 1                                                                    
    else:                                                                       
      j += 1                                                                    
  return [i,j-1]

def swap(A,i,j):
  A[i],A[j] = A[j],A[i]

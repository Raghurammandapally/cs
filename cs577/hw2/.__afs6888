import pdb
import math

def FindMaxRect(A):
  max = FindMaxRectRec(A,0,len(A)-1)
  return max

def FindMaxRectRec(A,start,end):
  if(start==end):
    return A[start]
  mid = int(math.floor((start+end)/2))
  maxL = FindMaxRectRec(A,start,mid)
  maxR = FindMaxRectRec(A,mid+1,end)
  pdb.set_trace()
  maxC = max(maxL,maxR)
  l = mid
  r = mid+1
  while(l>=start and r<=end):
    if(A[l]<A[r]):
      value = A[r]
      while(r<=end and A[l]<=A[r]):
          r=r+1
    elif(A[l]>A[r]):
      value = A[l]
      while(l>=start and A[l]>=A[r]):
          l=l-1
    else:
      value = A[r]
      while(r<=end and A[r]>=value):
        r = r+1
      while(l>=start and A[l]>=value):
        l = l-1
    m = value*(r-l-1)
    maxC = max(maxC,m)
  while(l>=start):
    value = A[l]
    while(l>start and value<=A[l-1]):
      l = l-1
    l = l-1
    m = value*(r-l-1)
    maxC = max(maxC,m)
  while(r<=end):
    value = A[r]
    while(r<end and value<=A[r+1]):
      r= r+1
    r= r+1
    m = value*(r-l-1)
    maxC = max(maxC,m)
  print("max of " + str(A) + " on [" + str(start) + ", " + str(end) + "] is " + str(maxC))
  return maxC

def BruteForce(A):
  B = [0]*len(A)
  for i in range(0,len(A)):
    l=0
    r=0
    while((i+r)<len(A)-1 and A[i+r+1]>=A[i]):
      r = r+1
    while((i-l)>=1 and A[i-l-1]>=A[i]):
      l = l+1
    B[i] = A[i]*(r+l+1)
  return max(B)

def compare(A):
  print("***************************")
  print(A)
  print("***************************")
  haruki = FindMaxRect(A)
  brute = BruteForce(A)
  print("Haruki: "+str(haruki))
  print("Brute force: "+str(brute))

#compare([7,1,2,5,4,8,6,3])

FindMaxRect([7,1,2,5,4,8,6,3])

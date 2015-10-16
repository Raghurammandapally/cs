import random
import csv
import time
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
  maxC = max(maxL,maxR)
  l = mid
  r = mid+1
  while(l>=start and r<=end):
    if(A[l]<A[r]):
      value = A[r]
      while(r<=end and value<=A[r]):
          r=r+1
    elif(A[l]>A[r]):
      value = A[l]
      while(l>=start and A[l]>=value):
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
  #print("max of " + str(A) + " on [" + str(start) + ", " + str(end) + "] is " + str(maxC))
  return maxC

"""
def FindMaxRectRec(A,start,end):
  if(start==end):
    return A[start]
  mid = int(math.floor((start+end)/2))
  maxL = FindMaxRectRec(A,start,mid)
  maxR = FindMaxRectRec(A,mid+1,end)
  maxC = max(maxL,maxR)
  l = mid
  r = mid+1
  while(l>=start or r<=end):
    if(r>end):
      R = -1
    else:
      R = A[r]
    if(l<start):
      L = -1
    else:
      L = A[l]
    value = max(L,R)
    if(L<=R):
      while(r<=end and A[r]>=value):
        r = r+1
    if(L>=R):
      while(l>=start and A[l]>=value):
        l = l-1
    m = value*(r-l-1)
    maxC=max(maxC,m)
  return maxC
"""

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
  start = time.time()
  dAndC = FindMaxRect(A)
  end = time.time()
  startBrute = time.time()
  brute = BruteForce(A)
  endBrute = time.time()
  print("testing an array of length " + str(len(A)) + "...")
  if(dAndC!=brute):
    print("ERROR!")
    print("  " + str(A))
    print("  D&C: "+str(dAndC))
    print("  Brute force: "+str(brute))
  return [end-start,endBrute-startBrute]

def test():
  runtimes = []
  for i in range(100):
    n = random.randint(0,100000)
    runtime = compare([random.randint(1,10000) for j in range(n)])
    runtimes.append([n,runtime[0], runtime[1]])

  with open('runtime.csv', 'wb') as csvfile:
    for line in runtimes:
        outwriter = csv.writer(csvfile)
        outwriter.writerow(line)

test()


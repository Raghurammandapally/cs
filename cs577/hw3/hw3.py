import pdb
import math
import random

# n is the number of elements we are looking to have sum to 1
def getRand(n, rnd):
  #mean = a/a+b
  # let's shoot for a total of 3, to make sure that we get enough elements
  # mean = 3/n
  # b = a/mean - a
  #   = 3*a(n-1)
  #   = 6*(n-1)
  if(n<1):
    raise Exception("bad input")
  samples = [random.random() for i in range(0,n)]
  total = sum(samples)
  samples = [s/total for s in samples]
  samples = [round(s,rnd) for s in samples]
  total = sum(samples)
  #make sure that the sum is exact
  delta = 0.00001
  while abs(total-1.0)>delta:
    end = len(samples)-1
    if(total < 1.0):
      samples[end] += 1.0 - total
    #sum is too large
    else:
      while(total > 1.0):
        maxOfArray = max(samples)
        samples[samples.index(maxOfArray)] = max(0, 1.0 - (total - maxOfArray))
        total = sum(samples)
      samples[end] = 1.0 - (total-samples[end])
    total = sum(samples)
  if(abs(total - 1.0) > delta):
    raise Exception("precision not reached!")
  return samples

'''
for N in range(100000):
  n = random.randint(5,100000)
  print("getting random array of size " + str(n))
  samples = getRand(n, 3)
'''


"""

def printArr(A):
  print(["%.3f" % num for num in A])

wi = getRand(20,3)
ai = [random.randint(-20,20) for i in range(0,len(wi))]

w = 5

start = 0
while(start < len(ai)):
  printArr(wi[start:min(start+w, len(wi)-1)])
  start += w

"""

def bruteForceWeightedMedian(a,w):
  [sortA,sortW] = zip(*sorted(zip(a,w)))
  ptr = 0
  sum = 0
  while(sum<0.5):
    sum += sortW[ptr]
    ptr += 1
  return sortA[ptr - 1]

#example from pset
#print(bruteForceWeightedMedian([40,-5,4,0,2.5,6,-2],[.25,.1,.05,.18,.15,.2,.07]))


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

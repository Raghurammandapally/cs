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

def generateInput(n=random.randint(5,100000)):
  W = getRand(n,3)
  A = [(random.random() - 0.5)*100000 for i in range(0,len(wi))]
  return [A,W]

from hw3 import weightedMedian, tol
from generateInput import generateInput

#the "true" weighted median, i.e. brute forced
def bruteForce(a,w,target=0.5):
  [sortA,sortW] = zip(*sorted(zip(a,w)))
  total = 0
  ptr = 0
  #while( total < target )
  while(tol < target - total):
    total += sortW[ptr]
    ptr += 1
  return sortA[ptr - 1]


#######################################
#         TEST CASES                  #
#######################################

# sample size of 1000
N = 1000
for i in range(1,N+1):
  # generateInput returns a 2-tuple of values a and weights w, such that the 
  # weights add up to 1. e.g.:
  #   >>> generateInput(4)
  #   [[-48889.55578699512, -17514.74434577306, -14318.16962721838, 
  #   49178.17800571047], [0.366, 0.134, 0.39, 0.11]]
  [a,w] = generateInput(i)
  a_orig = a[:]
  w_orig = w[:]
  wm = weightedMedian(a,w)
  bwm = bruteForce(a,w)
  if wm != bwm:
    print("Brute force produced %d; you produced %d.\n A: %s\n W: %s" % 
        (bwm,wm,str(a_orig),str(w_orig)))
print("Done testing!")

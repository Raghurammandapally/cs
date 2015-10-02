def dnf(a,lo,hi,pivot):
  pivotValue = a[pivot]
  i = lo
  j = lo
  n = hi
  while(j <= n):
    if(a[j] < pivotValue):
      swap(a,i,j)
      i += 1
      j += 1
    elif a[j] > pivotValue:
      swap(a,j,n)
      n -= 1
    else:
      j += 1
  print(a)
  print("i: %d; j: %d; n: %d" % (i,j,n))


def swap(a,i,j):
  a[i],a[j] = a[j],a[i]

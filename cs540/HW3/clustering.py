names_orig = ["BOS","NY","DC","PIT","CHI","SEA","SF","LA","HOU"]
dists_orig = [ [0, 206, 429, 572, 963, 2976, 3095, 2979, 1849],
          [None, 0, 233, 372, 802, 2815, 2934, 2786, 1628],
          [None, None, 0, 242, 671, 2684, 2799, 2631, 1408],
          [None, None, None, 0, 461, 2529, 2576, 2427, 1336],
          [None, None, None, None, 0, 2013, 2142, 2054, 1082],
          [None, None, None, None, None, 0, 808, 1131, 2314],
          [None, None, None, None, None, None, 0, 379, 1927], 
          [None, None, None, None, None, None, None, 0, 1547],
          [None, None, None, None, None, None, None, None, 0]
      ]
"""
names_orig = ["BA", "FI", "MI", "NA", "RM", "TO"]
dists_orig = [[0,662,877,255,412,996],
         [None, 0,295,468,268,400],
         [None, None, 0,754,564,138],
         [None, None, None, 0,219,869],
         [None, None, None, None, 0,669],
         [None, None, None ,None, None, 0]
     ]
"""
names = [ [n] for n in names_orig]
dists = [d[:] for d in dists_orig]

def idx(a):
    return names_orig.index(a)

def dist(a, b):
    i1 = idx(a)
    i2 = idx(b)
    if i1 < i2:
        i = i1
        j = i2
    else:
        i = i2
        j = i1
    return dists_orig[i][j]

#i,j should be ints in [0,len(names))
def merge(i,j):
    if i > j:
        i,j = j,i
    # now i < j
    i1 = 0
    j1 = i
    comb = names[i][:]
    comb.extend(names[j][:])
    while i1 < j1:
        dists[i1][j1] = min([dist(a,b) for a in names[i1] for b in comb if dist(a,b) > 0])
        i1 += 1
    # now i1 == j1
    j1 += 1
    while j1 < len(names):
        dists[i1][j1] = min([dist(a,b) for a in names[j1] for b in comb if dist(a,b) > 0])
        j1 += 1

    names[i].extend(names[j])
    for d in dists:
        del d[j]
    del dists[j]
    del names[j]

def pretty_print():
    print_names = ['/'.join(n) for n in names]
    maxes = [max(len(print_names[i]), max([len(str(dists[i][j])) for j in range(i+1)])) for i in range(len(names))]
    maxes = [m + 1 for m in maxes]
    #print maxes

    #max_len = max([len(l) for l in print_names])
    #max_len = max(max_len, max([len(str(x)) for x in l for l in dists]), 4)
    #max_len += 1

    max_len = max(maxes)
    
    fmt = ("%%%ds" * (len(names)+1)) % tuple([max_len] + maxes)

    #fmt = ("%%%ds" % max_len) * len(names)
    print fmt % tuple([""] + ['/'.join(n) for n in names])
    #for d in dists:
    for i in range(len(names)):
        print fmt % tuple(['/'.join(names[i])] + [str(x) if x is not None else "" for x in dists[i]])
    print "\n"

def find_max():
    minimum = 9999999999999999999
    i_ret = -1
    j_ret = -1
    for j in range(len(names)):
        for i in range(j):
            if 0 < dists[i][j] < minimum:
                minimum = dists[i][j]
                i_ret = i
                j_ret = j
    return [i_ret,j_ret,minimum]
            

def cluster():
    pretty_print()
    while len(names) > 2:
        [i,j,minimum] = find_max()
        print "Merging %s with %s (shortest distance at %d miles):" % ('/'.join(names[i]), '/'.join(names[j]), minimum)
        print "------------------------------------------------------------------"
        merge(i,j)
        pretty_print()


cluster()

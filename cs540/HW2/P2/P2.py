import random

# flips the boolean for each value in the list s, returns a
# list of these lists
def succ(s):
    ret = []
    for i in range(len(s)):
        to_add = s[:]
        to_add[i] = 0 if s[i] else 1
        ret.append(to_add)
    return ret

def hill_climb(init, fn, n, p=False):
    while(True):
        val = fn(*init)
        if p:
            print "Current state: %s; f(s): %d" % (str(init), val)

        if val == n:
            if p:
                print "I found an optimal solution! %s" % str(init)
            return (0, init)
        else:
            succs = succ(init)
            vals = [(s, fn(*s)) for s in succs]
            if p:
                print "Successors before filtering on f(s):"
                for s in vals:
                    print "  %s [%d]" % (str(s[0]), s[1])

            vals = [s for s in vals if s[1] > val]
            if not vals:
                if p:
                    print "Reached a local optimum! %s" % str(init)
                return (-1, init)
            if p:
                print "Successors after filtering:"
                for s in vals:
                    print "  %s [%d]" % (str(s[0]), s[1])
            init = random.choice(vals)[0]

def c_sat(a, b, c, d):
    ret = \
          (1 if a or not b else 0) + \
          (1 if not a or not c else 0) + \
          (1 if b or c else 0) + \
          (1 if b or not c else 0) + \
          (1 if not b or d else 0)
    return ret

def d_sat(a, b, c):
    ret = \
          (1 if a or b or not c else 0) + \
          ( 1 if not a or not b or not c else 0) + \
          ( 1 if not a or b or c else 0) + \
          ( 1 if not a or b or not c else 0)
    return ret

start_c = [0,1,0,0]
hill_climb(start_c, c_sat, 5, True)

hill_climb([1,1,0,0], c_sat, 5)
hill_climb([0,0,0,0], c_sat, 5, True)
hill_climb([0,1,0,1], c_sat, 5)

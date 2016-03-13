
"""
class1
class2
class3
Training Set Points
(2, 2), (4, 4), (2, 4)
(6, 5), (5.4, 5.6), (3.6, 6.4)
(1.8, 8), (5.6, 8.2)
"""
class1 = [[2,2],[4,4],[2,4]]
class2 = [[6,5],[5.4,5.6],[3.6,6.4],[4.4,6]]
class3 = [[1.8,8],[5.6,8.2]]

train = [class1, class2, class3]

def dist(p1,p2):
    return abs(p2[0]-p1[0])+abs(p2[1]-p1[1])

test = [[3,3], [6, 4.4], [2.6, 6]]

print "\\begin{enumerate}[(i)]"
for t in test:
    print "\\item \\texttt{Distances from %s to...\\\\" % str(t)
    for i in range(len(train)):
        print "  class%d: \\\\" % (i + 1)
        for c in train[i]:
            print "  -->%11s: %.1f ($|%.1f-%.1f| + |%.1f-%.1f| = %.1f$)\\\\" % (str(c), dist(t,c), t[0],c[0],t[1],c[1], dist(t,c))
    print "}"
print "\\end{enumerate}"

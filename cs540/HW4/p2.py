import math

# sigmoid function
def g(x):
  return 1/(1+math.exp(-x))

# derivative of sigmoid function
def g_prime(x):
  return g(x)*(1-g(x))

# learning rate
alpha = 0.2

# initialize weights
w = {"wx1a": 0.4, \
    "wx1b": -0.2,\
    "wx2a": 0.4,\
    "wx2b": -0.2,\
    "wx3a":0.4,\
    "wx3b":-0.2,\
    "wh1a":0.3,\
    "wh1b":0.3,\
    "wac": 0.4,\
    "wbc":-0.2,\
    "wh2c":0.3}

#for each example (x, y) in examples do
#    /* Propagate the inputs forward to compute the outputs */
#    for each node i in the input layer do
#        ai <--xi
aX1 = 0.3
aX2 = 0.8
aX3 = 0.1

#    for =2to L do
#        for each node j in layer do
#            inj <-- iwi,jai
#            aj  <--g(inj)
print "Inputs/outputs:"
inA = (aX1 * w["wx1a"]) + (aX2 * w["wx2a"]) + (aX3 * w["wx3a"]) + (1 * w["wh1a"])
print inA
# inA: 0.78
aA = g(inA)
print aA
# aA: 0.685680
inB = (aX1 * w["wx1b"]) + (aX2 * w["wx2b"]) + (aX3 * w["wx3b"]) + (1 * w["wh1b"])
print inB
# inB: 0.06
aB = g(inB)
print aB
# aB: 0.514995
inC = (aA * w["wac"]) + (aB * w["wbc"]) + (1 * w["wh2c"])
print inC
# inC: 0.471273
aC = g(inC)
print aC
# aC: 0.615685

#    /* Propagate deltas backward from output layer to input layer */
#    for each node j in the output layer do
#        (delta)[j] <--g (inj)*(yj - aj)
print "\nDeltas:"
dC = g_prime(inC)*(1-aC)
print dC
# dC: 0.090935

#    for =L-1to1do
#        for each node i in layer do
#            (delta)[i] <--g (ini) j wi,j (delta)[j]
dA = g_prime(inA)*w["wac"]*dC
print dA
# dA: 0.007839
dB = g_prime(inB)*w["wbc"]*dC
print dB
# dB: -0.004543
dBias = g_prime(1)*w["wh2c"]*dC
print dBias
# dBias: 0.005364

#    /* Update every weight in network using deltas */
#    for each weight wi,j in network do
#        wi,j <--wi,j+alpha*ai*(delta)[j]

print "\nWeights:"
w["wx1a"] += alpha * aX1 * dA
w["wx1b"] += alpha * aX1 * dB
w["wx2a"] += alpha * aX2 * dA
w["wx2b"] += alpha * aX2 * dB
w["wx3a"] += alpha * aX3 * dA
w["wx3b"] += alpha * aX3 * dB
w["wh1a"] += alpha * 1 * dA
w["wh1b"] += alpha * 1 * dB
w["wac"] += alpha * aA * dC
w["wbc"] += alpha * aB * dC
w["wh2c"] += alpha * 1 * dC

print "Weights to hidden layer:"
for k in ["wx1a", "wx1b", "wx2a", "wx2b", "wx3a", "wx3b", "wh1a", "wh1b"]:
  print "%s = %.6f" % (k, w[k])

print "\nWeights to output layer:"
for k in ["wac", "wbc", "wh2c"]:
  print "%s = %.6f" % (k, w[k])

print "\nBiases:"
print "dA: %.6f" % dA
print "dB: %.6f" % dB
print "dBias: %.6f" % dBias
print "dC: %.6f" % dC

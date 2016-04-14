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
inA = (aX1 * w["wx1a"]) + (aX2 * w["wx2a"]) + (aX3 * w["wx3a"]) + (1 * w["wh1a"])
print inA
# inA: 0.78
aA = g(inA)
print aA
# aA: 0.6857
inB = (aX1 * w["wx1b"]) + (aX2 * w["wx2b"]) + (aX3 * w["wx3b"]) + (1 * w["wh1b"])
print inB
# inB: 0.06
aB = g(inB)
print aB
# aB: 0.5150
inC = (aA * w["wac"]) + (aB * w["wbc"]) + (1 * w["wh2c"])
print inC
# inC: 0.4713
aC = g(inC)
print aC
# aC: 0.6157

#    /* Propagate deltas backward from output layer to input layer */
#    for each node j in the output layer do
#        (delta)[j] <--g (inj)*(yj - aj)
dC = g_prime(inC)*(1-aC)
print dC
# dC: 0.9093

#    for =L-1to1do
#        for each node i in layer do
#            (delta)[i] <--g (ini) j wi,j (delta)[j]
dA = g_prime(inA)*dC
print dA
# dA: 0.0196
dB = g_prime(inB)*dC
print dB
# dB: 0.0227
dBias = g_prime(1)*dC
print dBias
# dBias: 0.0179

#    /* Update every weight in network using deltas */
#    for each weight wi,j in network do
#        wi,j <--wi,j+alpha*ai*(delta)[j]
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

for k in sorted(w):
  print "\\item $%s = %.4f$" % (k, w[k])

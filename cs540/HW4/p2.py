import math

# sigmoid function
def s(x):
  return 1/(1+math.exp(-x))

# derivative of sigmoid function
def s_prime(x):
  return s(x)*(1-s(x))

# learning rate
alpha = 0.2

# inputs for input nodes
aX1 = 0.3
aX2 = 0.8
aX3 = 0.1

# weights
w = {"wx1a": 0.4, \
"wx1b": -.2,\
"wx2a": 0.4,\
"wx2b": -0.2,\
"wx3a":0.4,\
"wx3b":-0.2,\
"wh1a":0.4,\
"wh1b": -0.2,\
"wac": 0.4,\
"wbc":-0.2,\
"wh2c":0.3}

# total input for hidden nodes
inA = (aX1 * w["wx1a"]) + (aX2 * w["wx2a"]) + (aX3 * w["wx3a"]) + (1 * w["wh1a"])
inB = (aX1 * w["wx1b"]) + (aX2 * w["wx2b"]) + (aX3 * w["wx3b"]) + (1 * w["wh1b"])

# output for hidden nodes
aA = s(inA)
aB = s(inB)

# input for output node
inC = (aA * w["wac"]) + (aB * w["wbc"]) + (1 * w["wh2c"])

# output for output node
aC = s(inC)

# calculating deltas for output node
dC = s_prime(inC)*(1-aC)

# calculating deltas for hidden nodes
dA = s_prime(inA)*dC
dB = s_prime(inB)*dC
dBias = s_prime(1)*dC

# finally, calculating new weights
# input -> hidden
w["wx1a"] += alpha * aX1 * dA
w["wx1b"] += alpha * aX1 * dB
w["wx2a"] += alpha * aX2 * dA
w["wx2b"] += alpha * aX2 * dB
w["wx3a"] += alpha * aX3 * dA
w["wx3b"] += alpha * aX3 * dB
w["wh1a"] += alpha * 1 * dA
w["wh1b"] += alpha * 1 * dB

# hidden -> output
w["wac"] += alpha * aA * dC
w["wbc"] += alpha * aB * dC
w["wh2c"] += alpha * 1 * dC

for k in sorted(w):
  print "%s:\t%.4f" % (k, w[k])

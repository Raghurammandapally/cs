

def p(a, b, c, d, e):
  ret = 1.0

  # A
  if a:
    ret *= 0.8
  else:
    ret *= 0.2

  # B
  if a:
    if b: ret *= 0.2
    else: ret *= 0.8
  else:
    if b: ret *= 0.9
    else: ret *= 0.1

  # D
  if a and b:
    if d: ret *= 0.85
    else: ret *= 0.15
  elif a and not b:
    if d: ret *= 0.25
    else: ret *= 0.75
  elif not a and b:
    if d: ret *= 0.15
    else: ret *= 0.85
  else:
    if d: ret *= 0.05
    else: ret *= 0.95

  # C
  if b:
    if c: ret *= 0.95
    else: ret *= 0.05
  else:
    if c: ret *= 0.5
    else: ret *= 0.5

  # E
  if d:
    if e: ret *= 0.6
    else: ret *= 0.4
  else:
    if e: ret *= 0.5
    else: ret *= 0.5

  return ret

def short(s):
  return "T" if s else "F"

latex = True

tot = 0.0

if latex:
  print "%s & %s & %s & %s & %s & %s\\\\" % tuple("ABCDEP")
else:
  print "%s\t%s\t%s\t%s\t%s\t%s" % tuple("ABCDEP")
for a in [True, False]:
  for b in [True, False]:
    for c in [True, False]:
      for d in [True, False]:
        for e in [True, False]:
          tot += p(a,b,c,d,e)
          if latex:
            #   T & T & T          &F  & F & F\\
            fmt = "%s & %s & %s & %s & %s & %f\\\\"
          else:
            fmt = "%s\t%s\t%s\t%s\t%s\t%f"

          print fmt % (short(a), short(b), short(c), short(d), short(e), p(a, b, c, d, e))

print tot


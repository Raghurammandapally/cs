f <- function(x) {
  x^2
}

x1 = c(0,3,4)
x2 = c(1,2)

y1 = sapply(x1, f)
y2 = sapply(x2, f)

jpeg('rplot.jpg')

#plot.new()

plot(x1, y1, pch=16, xlab="p", ylab="q")
points(x2, y2)

x = c(x1[c(1,2)],x2[c(1,2)])
y = c(y1[c(1,2)],y2[c(1,2)])
dy = 0.6
dx = dy/4
#draw squares over support vectors
for(i in 1:4) {
  rect(x[i] - dx, y[i] - dy, x[i] + dx, y[i] + dy)
}

text(x1, y1 + 1.5*dy, "-1")
text(x2, y2 - 1.5*dy, "+1")
text(x1[3], y1[3]-1.5*dy, "-1")

# line with slope of 3 and y-intercept of -1
# y = 3x - 1
abline(b=3, a=-1, lwd=45, col=rgb(0,1,0,0.5, maxColorValue=1))
abline(b=3, a=-1)

dev.off()

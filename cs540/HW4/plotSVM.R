f <- function(x) {
  x^2
}

x1 = c(0,3,4)
x2 = c(1,2)

y1 = sapply(x1, f)
y2 = sapply(x2, f)

jpeg('rplot.jpg')

#plot.new()

plot(x1, y1, pch=16)
points(x2, y2)

# line with slope of 3 and y-intercept of -1
# y = 3x - 1
abline(b=3, a=-1, lwd=45, col=rgb(0,1,0,0.5, maxColorValue=1))
abline(b=3, a=-1)

dev.off()

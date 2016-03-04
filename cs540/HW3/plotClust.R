x = c(0,206,429,572,963,2976,3095,2979,1849,
0,0,233,372,802,2815,2934,2786,1628,
0,0,0,242,671,2684,2799,2631,1408,
0,0,0,0,461,2529,2576,2427,1336,
0,0,0,0,0,2013,2142,2054,1082,
0,0,0,0,0,0,808,1131,2314,
0,0,0,0,0,0,0,379,1927,
0,0,0,0,0,0,0,0,1547,
0,0,0,0,0,0,0,0,0)

names = c("BOS","NY","DC","PIT","CHI","SEA","SF","LA","HOU")

dists <- matrix(x, ncol=9, byrow=FALSE)

colnames(dists) <- as.list(names)
rownames(dists) <- as.list(names)

d <- as.dist(dists)

hc = hclust(d, method="single")

jpeg('rplot.jpg')
plot(hc)
dev.off()

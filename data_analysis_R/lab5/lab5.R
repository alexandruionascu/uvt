# Exercise 1
contamined <- c(600, 590, 570, 570, 565, 580)
background <- c(560, 550, 570, 550, 570, 590, 550, 580)
var(background) # 228.57
var(contamined) # 184.16

# Exercise 2
x <- c(1.83, 0.50, 1.62, 2.48, 1.68, 1.88, 1.55, 3.06, 1.30)
y <- c(0.878, 0.647, 0.598, 2.05, 1.06, 1.29, 1.06, 3.14, 1.29)
ks.test(x, y) # 0.03663 < .05
# x and y are from different distributions 

# Exercise 3
tbl <- table(quine[quine$Sex == "F",]$Days, quine$Eth)
chisq.test(tbl) # p=0.1682 > 0.005

# Exercise 4
df <- data.frame(InsectSprays)
x1 <- tapply(df$count, df$spray, mean)
x2 <- tapply(df$count, df$spray, var)
x3 <- tapply(df$count, df$spray, function(sprays) length(unique(sprays)))

sprays <- levels(factor(df$spray))
boxplot(x1 ~ sprays) # mean
boxplot(x2 ~ sprays) # variance
boxplot(x3 ~ sprays) # count

aov.out <- aov(count ~ df$spray, data=df)
summary(aov.out)
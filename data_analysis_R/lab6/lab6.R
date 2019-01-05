# Exercise 1 -- same as # 3 from Lab 5 
tbl <- table(quine[quine$Sex == "F",]$Days, quine$Eth)
chisq.test(tbl) # p=0.1682 > 0.005

# Exercise 2 -- same as # 4 from Lab 5
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

# Exercise 3
setwd("~/Workspace/uvt/data_analysis_R/lab6/")
poverty <- read.table("./poverty.txt", header=T, sep="\t")
# scatterplot each combination of variables
# plot(poverty)
# scatterplot two variables
plot(poverty$TeenBrth ~ poverty$PovPct)
# correlation of two variables
cor(poverty$TeenBrth, poverty$PovPct) # 0.70
fit <- lm(TeenBrth ~ PovPct, data=poverty)
# add regresion line to the scatterplot
abline(fit)
# predict birthrate given poverty rate
predict(fit, newdata = data.frame(PovPct = 12.7)) # 41.39

# Exercise 4
# extract data as data frame
df <- data.frame(mtcars)
fit <- lm(mpg ~ disp + hp + wt, data=df)
# compute correlations for each pair of variables
cor(df)
# redo the model without the variable that fails the t-test
fit <- lm(mpg ~ hp + wt, data = df)
# predict the mileage
predict(fit, newdata = data.frame(hp = 102, wt=2.91)) # 22.7
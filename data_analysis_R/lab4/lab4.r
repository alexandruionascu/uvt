# Exercise 1
# a
iris_df <- data.frame(iris)
mean <- apply(iris_df['Petal.Length'], 2, mean)
median <- apply(iris_df['Petal.Length'], 2, median)
mode <- apply(iris_df['Petal.Length'], 2, mode)
# b
range <- apply(iris_df['Sepal.Length'], 2, range)
variance <- apply(iris_df['Sepal.Length'], 2, var)
standard.deviation <- apply(iris_df['Sepal.Length'], 2, sd)
iqr <- apply(iris_df[iris_df$Species == 'setosa', ]['Sepal.Length'], 2, IQR)
# c
library(propagate)
sk <- apply(iris_df['Petal.Width'], 2, skewness)
k <- apply(iris_df['Petal.Width'], 2, kurtosis)

# Exercise 2
hist(iris_df$Petal.Length,
     border="blue", 
     col="green",
     las=1, 
     breaks=50,
     freq=FALSE
     )

lines(density(iris_df$Petal.Length, na.rm=TRUE), col="blue")
boxplot(Sepal.Length ~ Species, data=iris_df)

# Exercise 3
infert_df <- data.frame(infert)
t <- xtabs(~education + spontaneous, infert_df)
margin.table(t, 1)

# Exercise 4

verginica <- iris_df[iris_df$Species == 'virginica', ]
setosa <- iris_df[iris_df$Species == 'setosa', ]
cov(verginica$Petal.Length, verginica$Sepal.Length)
cov(setosa$Petal.Length, setosa$Sepal.Length)

# Exercise 5
cov(verginica$Petal.Length, verginica$Sepal.Length)
cov(setosa$Petal.Length, setosa$Sepal.Length)

# Exercise 6
library(DAAG)
ais_df <- data.frame(ais)
pairs(~rcc+wcc+hc+hg+ferr+bmi+ssf+pcBfat+lbm+ht+wt,data=ais_df,
      main="Simple Scatterplot Matrix")
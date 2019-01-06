# Exercise 1
setwd("~/Workspace/uvt/data_analysis_R/lab7/")
ds <- read.csv("logregr1.csv")
fit <- glm(hon ~ female + math, data=ds)
plot(fit)
# expected for female: 1.27
# expected for math: 1.02
exp(fit$coefficients)

# Exercise 2
df <- data.frame(iris)
df$Species <- as.numeric(df$Species)
plot(df) # species are the response variable
# split data in two parts, train and test
library(caret)
test.train <- createDataPartition(y=df$Species,p=0.8,list=FALSE)
train <- df[test.train,]
test <- df[-test.train,]

library(class)
# accuracy 1.0 for K=1,2,3
for (k in 1:3) {
  predicted <- knn(train, test, cl=train$Species, k=k)
  # printing confusion matrix
  print(confusionMatrix(table(predicted, test$Species)))
}

# Exercise 3
df <- read.csv("database.csv", header=FALSE)
df[is.na(df)] <- 0
df$V7 <- as.numeric(df$V7)
test.train <- createDataPartition(y=df$V11,p=0.8,list=FALSE)
train <- df[test.train,]
test <- df[-test.train,]

library(class)
# best accuracy: K = 5, accuracy: 0.64
for (k in 1:10) {
  predicted <- knn(train, test, cl=train$V11, k=k)
  # printing confusion matrix
  print(confusionMatrix(table(predicted, test$V11)))
}

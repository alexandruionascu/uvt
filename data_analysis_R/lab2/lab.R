#Exercise 1

# load iris as data frame
iris_df <- data.frame(iris)
means <- colMeans(iris_df[1:4])

#Exercise 2

b1 <- data.frame(beaver1)
b2 <- data.frame(beaver2)

b1$id <- rep(1, length(b1$activ))
b2$id <- rep(2, length(b2$activ))

merged <- rbind(b1,b2)
active <- merged[merged$activ == 1,]

# Exercise 3
is_perfect_square <- function(x) {
  for (i in 1:x) {
    if (i * i == x) {
      return(TRUE)
    }
  }
  return(FALSE)
}

result <- list()
for(i in 0:9)
  result <- c(result, list(Filter(is_perfect_square, (i * 10):(i * 10 + 9))))

# Exercise 4
ex_4_res <- diag(c(10:0, 1:10))

# Exercise 5
m1 <- diag(x=1, nrow=20, ncol=21)
m1_prime <- rbind(0, m1)
m1_second <- rbind(m1, 0)
wilkinson_matrix <- m1_prime + m1_second + ex_4_res
eigenvalues <- eigen(wilkinson_matrix)

# Exercise 6
library(DAAG)
with(rainforest, table(complete.cases(root), species))
rainforest_df <- data.frame(rainforest)
filtered_df <- na.omit(rainforest_df)
print(aggregate(filtered_df[0: 1], by=list(filtered_df$species), FUN=length))
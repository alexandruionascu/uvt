# Ex 1
len <- 30
fib_vals <- c()
fib_vals[1] <- 1
fib_vals[2] <- 1
for (i in 3:len) { 
  fib_vals[i] <- fib_vals[i-1] + fib_vals[i-2]
} 
golden_seq <- c()
for (i in 2:len) {
  golden_seq[i] <- fib_vals[i] / fib_vals[i - 1]
}

# Ex 2
is_prime <- function(x) {
  for (i in 2:(x-1)) {
    if (x %% i == 0) {
      return(FALSE)
    }
  }
  return(TRUE)
}

for (i in 2:1998) {
    if (is_prime(i) && is_prime(i + 2)) {
      print(c(i, i + 2))
    }
}

# Ex 3
monthly_mortgage <- function (n, P, open) {
  return(P * i / (1 - 1 + (if (open==TRUE) 0.005 else 0.004)^(-n)))
}

#########################################################################################

# Ex 1
U1 <-runif(10^4)
U2 <- runif(10^4)

a <- weighted.mean(U1 + U2, rep(10^(-4), 10^4))
b <- var(U1 + U2)
b_prime <- var(U1) + var(U2)
c <- dnorm(U1 + U2)
d <- dnorm(sqrt(U1) + sqrt(U2))

# Ex 2
discreteunif <- round(runif(1000) * 10)
table(discreteunif) # there are fewer 0s and 10s, we shall increase the bounds / introduce a offset value

# Ex 3
bulbs <- sample(c(0,1), size=500, replace=TRUE, prob=c(0.01,0.99))
e <- sum(bulbs * 0.99) / 500
v <- var(bulbs)

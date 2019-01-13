from pulp import *
import numpy as np
prob = LpProblem("N-Queens", LpMinimize)
table_size = 8
queens = np.array([[
	LpVariable("q_{0}{1}".format(i, j), 0, 1, LpInteger)
	for j in range(table_size)]
	for i in range(table_size)
])


cost = [[j for j in range(table_size)] for i in range(table_size)]


# objective function
prob += lpSum([
	cost[i][j] * queens[i][j]
	for i in range(table_size)
	for j in range(table_size)
])
for i in range(table_size):
	# for rows
	prob += lpSum(queens[i]) == 1
	# for columns
	prob += lpSum(queens[:, i]) == 1
diags = [
	queens[::-1,:].diagonal(i)
	for i in range(-queens.shape[0] + 1, queens.shape[1])
]

diags.extend(queens.diagonal(i) for i in range(queens.shape[1] - 1,-queens.shape[0], -1))
# diagonals
for diag in diags:
	prob += lpSum(diag) <= 1

prob.solve()
print("Status:", LpStatus[prob.status]) 
for index, v in enumerate(prob.variables()):
	print('O|' if v.varValue == 0 else 'X|',end = '\n' if (index + 1) % table_size == 0 else '')
print("Min Sum of Y's = ", value(prob.objective))

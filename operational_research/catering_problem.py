from pulp import *
import numpy as np

prob = LpProblem("Catering", LpMinimize)
cost = [50, 90, 110]
people = [4, 9, 12]
x = [LpVariable("x_{0}".format(i), 0, None) for i in range(3)]

prob += lpSum([cost[i] * x[i] for i in range(3)])

prob += lpSum([people[i] * x[i] for i in range(3)]) >= 140

for i in range(3):
	prob += x[i] <= 10

def solve_lp_relaxation(additional_constraints = []):
	global prob, x
	for constraint in additional_constraints:
	prob += constraint
	prob.solve()
	print("Status:", LpStatus[prob.status])
	for index, v in enumerate(prob.variables()):
		print(v.varValue)
	print("Min cost = ", value(prob.objective))

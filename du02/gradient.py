from math import sqrt, atan2, degrees

matrix: list[list[int]] = [
    [2, 2, 2, 2, 2, 2],
    [2, 4, 4, 4, 4, 2],
    [2, 4, 4, 6, 4, 2],
    [2, 4, 6, 6, 6, 2],
    [2, 2, 6, 6, 6, 2],
    [2, 2, 2, 2, 2, 2]
]
magnitude = [[0 for i in range(len(matrix[0]))] for i in range(len(matrix))]
angle = [[0 for i in range(len(matrix[0]))] for i in range(len(matrix))]

for j in range(1, len(matrix) - 1):
    for i in range(1, len(matrix[0]) - 1):
        gradientX = (matrix[j][i+1] - matrix[j][i-1])/2
        gradientY = (matrix[j+1][i] - matrix[j-1][i])/2

        magnitude[j][i] = round(sqrt(gradientX**2 + gradientY**2))
        if magnitude[j][i] > 0:
            angle[j][i] = round(degrees(atan2(gradientY, gradientX)))
        

for row in magnitude:
    print(row)

print('\n')
for row in angle:
    print(row)


     
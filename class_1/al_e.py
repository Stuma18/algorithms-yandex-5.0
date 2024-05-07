# Прибыльный стартап
date = input().split()
n, k, d = int(date[0]), int(date[1]), int(date[2])

pri = []

n_new = n * 10
for i in range(10):
    n_new_new = n_new + i
    if n_new_new % k == 0:
        n = n_new_new
        result = str(n) + "0" * (d - 1)
        pri.append(result)
        break
    if i == 9 and n_new_new % k != 0:
        pri.append(-1)
print(pri[-1])

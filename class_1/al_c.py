#Форматирование

import math
n=int(input())
strings=[]
for i in range (n):
 strings.append(int(input()))

spaces=0
for i in range(n):
 if strings[i]%4==0:
  spaces = spaces + strings[i]/4
 if strings[i]%4==1:
  spaces = spaces + math.floor(strings[i]/4)+1
 if strings[i]%4==2:
  spaces = spaces + math.floor(strings[i]/4)+2
 if strings[i]%4==3:
  spaces = spaces + math.floor(strings[i]/4)+2

print(int(spaces))

# Матч

score_1 = str(input())
score_2 = str(input())
flag = int(input())

team_1 = int(score_1[0]) + int(score_2[0])
team_2 = int(score_1[-1]) + int(score_2[-1])

if team_1 > team_2:
 print(0)
if team_1 == team_2:
 if flag == 1:
  if int(score_2[0])>int(score_1[-1]):
    print(0)
  if int(score_2[0])==int(score_1[-1]):
   print(1)
  if int(score_2[0])<int(score_1[-1]):
    n = int(score_1[-1]) - int(score_2[0])
    print(n)
 

 if flag == 2:
  if int(score_1[0])>int(score_2[-1]):
    print(0)
  if int(score_1[0])==int(score_2[-1]):
   print(1)
  if int(score_1[0])<int(score_2[-1]):
    n = int(score_2[-1]) - int(score_1[0])+1
    print(n)

if team_1<team_2:
  if flag==1:
   n = team_2-team_1
   print(n)
  if flag==2:
   n = team_2 - team_1 + 1
   print(n)
 
        
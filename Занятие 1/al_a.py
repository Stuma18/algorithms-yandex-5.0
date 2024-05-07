
vasya =input().split()
masha = input().split()

p=int(vasya[0])
v=int(vasya[1])

q=int(masha[0])
m=int(masha[1])


if abs(p) > 100000 or abs(q) > 1000000:
    if p > q:
        if p - v > q + m:
            result_1 = 2 * v + 2 * m + 2
            print(result_1)
        elif p - v == q + m:
            result_1_2 = 2 * v + 2 * m + 1
            print(result_1_2)
    else:
        if q - m > p + v:
            result_2 = 2 * v + 2 * m + 2
            print(result_2)
        elif q - m == p + v:
            result_2_2 = 2 * v + 2 * m + 1
            print(result_2_2)
            
else:
    v_t=[]
    m_t=[]

    v_t.append(p)
    while v>0:
        v_t.append(p+v)
        v_t.append(p-v)
        v=v-1

    m_t.append(q)
    while m>0:
        m_t.append(q+m)
        m_t.append(q-m)
        m=m-1

    all = v_t + m_t
    all_new = list(set(all))

    result = len(all_new)
    #print(all)
    print(result)

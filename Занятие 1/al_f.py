# Миша и математика

n = int(input())
numbers = list(map(int, input().split(' ', n - 1)))

index_odd = n
index_even = 0
def find_first_even_index(arr):
    for i in range(len(arr)):
        if arr[i] % 2 == 0:
            return i
        
in_2 = find_first_even_index(numbers)

if in_2 is not None:
    for num in reversed(numbers):
        index_odd = index_odd - 1
        if num % 2 != 0:
            symbols = "x" * (index_odd - 1)
            if in_2 > index_odd:
                symbols =symbols + "x"

            break
    for i in range(len(numbers)):
            if numbers[i] % 2 == 0 and i > index_odd:
                    symbols = symbols + "+" *  (len (symbols) - (i-1))
                    break

    symbols = symbols + (n - 1 - len(symbols)) * str("+")
    print(symbols)
else:
    symbols = (n - 1) * str("x")
    print(symbols)

def solution(a, b, n):
    answer = 0;
    while (n >= a):
        x,y = divmod(n,a);
        n = x*b + y;
        answer += x*b;

    return answer

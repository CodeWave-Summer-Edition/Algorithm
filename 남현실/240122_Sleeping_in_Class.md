import java.util.*;
import java.io.*;

/*
최소의 수정으로 모든 숫자 동일하게 만들기
최대 합은 10^6이하
1. 전체 합의 약수를 구한다 (HashSet을 이용해 중복을 없앤다)
    ex) 1 2 3 1 1 1
    0 1 3 6 7 8 9
    - total: 9
    - 약수들: 1, 3, 9
      => 1이 9개, or 3이 3개 or 9이 1개
2. 각 약수가 구간합으로 나올 수 있도록 바꿔야하는 개수를 센다
    - 가능하면 바꿔야하는 개수로,
    - 불가능하면 -1을 리턴한다
3. 그리고 바꿔야하는 개수들 중 최소 값을 구한다
 */
public class Main {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        StringBuilder result = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            int N = Integer.parseInt(br.readLine());
            int[] classes = new int[N + 1]; // 누적합으로 구하기
            String[] temp = br.readLine().split(" ");
            for (int i = 1; i <= N; i++) {
                classes[i] = Integer.parseInt(temp[i - 1]) + classes[i - 1];
            }

            int total = classes[N];
            if (total == 0) { // 0이면 바꿀 필요없음
                result.append(0).append("\n");
                continue;
            }

            int min = Integer.MAX_VALUE;
            HashSet<Integer> divisors = getDivisors(total);
            for (int divisor : divisors) {
                int changeCnt = getChangeCnt(classes, divisor);
                if (changeCnt == -1) {
                    continue;
                }
                min = Math.min(min, changeCnt);
            }
            result.append(min).append("\n");
        }

        bw.write(result.toString());

        br.close();
        bw.close();
    }

    public static HashSet<Integer> getDivisors(int n) {
        HashSet<Integer> divisors = new HashSet<>();
        for (int i = 1; i * i <= n; i++) {
            if (n % i == 0) {
                divisors.add(i);
                divisors.add(n / i);
            }
        }
        return divisors;
    }

    public static int getChangeCnt(int[] classes, int divisor) {
        int start = 0;

        int changeCnt = 0;
        for (int end = 0; end < classes.length; end++) {
            int sum = classes[end] - classes[start];
            if (sum > divisor) {
                changeCnt = -1;
                break;
            }
            if(sum < divisor) {
                continue;
            }
            // sum == divisor
            changeCnt += end - start - 1;
            start = end;
        }
        return changeCnt;
        
        /*
        while (j < classes.length) {
            int sum = classes[j] - classes[i];
            if (sum == divisor) {
                changeCnt += j - i - 1;
                if (j == classes.length - 1) {
                    return changeCnt;
                }
                i = j;
                j = i + 1;
            } else if (sum < divisor) {
                j += 1;
            } else { // sum > divisor // 구간합으로 해당 약수가 나올 수 없는 경우
                break;
            }
        }
        return -1;
         */
    }
}

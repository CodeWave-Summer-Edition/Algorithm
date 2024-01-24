# Sleeping in class 풀이

```java
package Baekjoon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Algo24620 {

    private static int T, N;
    private static int[] accumulatedSum;
    static StringBuilder sb = new StringBuilder();

    public static void main(String[] args) throws IOException {
        init();
        System.out.println(sb);
    }
    private static void init() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;
        T = Integer.parseInt(br.readLine());
        for (int testCase = 1; testCase <= T; testCase++) {
            N = Integer.parseInt(br.readLine());
            accumulatedSum = new int[N + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= N; i++) {
                accumulatedSum[i] = accumulatedSum[i - 1] + Integer.parseInt(st.nextToken());
            }
            solve();
        }
    }

    private static void solve() {
        for (int divisor = N; divisor >= 1; divisor--) {
            if (isSleptEqually(divisor)) {
                sb.append(N - divisor).append("\n");
                return;
            }
        }
    }

    private static boolean isSleptEqually(int divisor) {
        if (accumulatedSum[N] % divisor != 0) return false;
        int start = 0;
        int end;

        for (end = 1; end <= N; end++) {
            if (accumulatedSum[end] - accumulatedSum[start] > accumulatedSum[N]/divisor) return false;
            if (accumulatedSum[end] - accumulatedSum[start] < accumulatedSum[N]/divisor) continue;
            start = end;
        }
        return start == end - 1;
    }
}

```

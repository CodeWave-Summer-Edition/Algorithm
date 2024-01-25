[문제 링크](https://www.acmicpc.net/submit/24620)
## 문제 인식
인접한 원소들을 합치는 과정을 통해 모든 원소를 동일하게 만드는 최소 횟수를 구해라

## 문제 접근
문제는 다음과 같았다.   
1. 배열에서 인접한 원소를 합쳐야한다.
2. 모든 원소가 동일하게 만들어야 한다.

이 두가지를 보고 다음과 같은 접근방식을 생각해봤다.   
### Union-find와 Priority-Queue를 이용한 그리디한 문제 해결 ❌
이 방법은 가장 작은 값들을 우선적으로 선택하여 인접한 원소를 더해간다는 아이디어이다.   
하지만 이는 최적해가 나온다는 보장이 없다.

### Parametric-Search 를 이용한 문제 해결 ❌
이 방법은 이분탐색을 통해 `만들고자 하는 수`의 값을 조절해가며 문제를 해결해나가자는 아이디어다.   
하지만 이 방법 또한 `언제 만들고자 하는 수의 값을 늘려야하며, 언제 줄여야 하는지` 에 대한 기준이 명확하지 않다.   
이 방법이 맞겠다! 싶어서 매우 많은 트라이를 했으나 토마토 페스티벌을 열어버렸다.
![image](https://github.com/CodeWave-Summer-Edition/Algorithm/assets/43038815/53f81baf-4e68-4552-8be2-ef32fb0187f0)

### 약수를 이용한 접근 ⭕
Parametric-Search를 이용해서 문제를 해결하다가 문득 떠오른 아이디어다.   
위에서 실패한 이유가 `만들고자 하는 수 보다 커지는 경우와 작아지는 경우가 있는데, 이것만을 가지고 늘릴지 줄일지 판별할 기준이 명확하지 않다` 라는 이유였다.
그러면 딱 나누어 떨어지도록 만들 수 있으면 OK, 안만들어 진다면 NO 라는 명확한 기준을 정했다.

그렇다면, 딱 나누어 떨어지도록 만들기 위해서는 어떻게 해야할까? 답은 모든 원소들의 합을 구한 뒤, 약수로 나누어 떨어지게 만들자 였다.

예를들어 모든 원소의 합이 15라고 가정해보자. 15의 약수는 1, 3, 5, 15 가 있다. 그러면 해당 원소를 `A개의 1`, `B개의 3`, `C개의 5`, `D개의 15` 로 정확하게 나눌 수 있다면 OK인 것이다.

코드는 아래와 같이 구현했다.

#### 변수 ❗
이 아이디어의 변수는 0 이다. 0은 약수가 존재하지 않는다. 따라서 배열의 모든 원소의 합이 0이라면 바로 0을 구해줬다. 또한 원소 사이에 0이 존재하는 경우도 고려해서 문제를 해결했다.

## 코드 O(N)
```java
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 0; test_case < T; test_case++) {
            int n = Integer.parseInt(br.readLine());
            int result = n - 1;
            int sum = 0;
            int[] array = new int[n];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(st.nextToken());
                sum += array[i];
            }
            if (sum == 0) {
                result = 0;
            } else {
                List<Integer> measures = getMeasures(sum);

                for (int measure : measures) {
                    int count = 0;
                    int tempSum = array[0];
                    boolean possible = true;

                    for (int i = 1; i < n; i++) {
                        if (tempSum + array[i] > measure) {
                            if (tempSum != measure) {
                                possible = false;
                                break;
                            }
                            tempSum = array[i];
                        } else {
                            tempSum += array[i];
                            count++;
                        }

                        if (i == n - 1) {
                            if (tempSum != measure) {
                                possible = false;
                            }
                        }
                    }

                    if (possible) {
                        result = Math.min(result, count);
                    }
                }
            }
            sb.append(result).append('\n');
        }
        bw.write(sb.toString());
        br.close();
        bw.close();
    }

    //해당 number의 약수들을 모조리 구함
    private static List<Integer> getMeasures(int number) {
        List<Integer> measures = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            if (number % i == 0) {
                measures.add(i);
            }
        }
        return measures;
    }
}
```

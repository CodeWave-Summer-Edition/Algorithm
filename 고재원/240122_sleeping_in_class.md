# 
![image](https://github.com/CodeWave-Summer-Edition/Algorithm/assets/29039922/3fc53c8f-91fc-4e36-97d5-28d7fe0ff5ec)


# 생각
1. 문제 이해
   - 1차원 배열의 원소를 더하여 배열 내의 모든 원소의 값이 같도록 만들 때, 조건을 만족하는 최소 합침의 수 구하기
2. 문제 풀이
   - 최소를 구하라 -> 완전탐색?
   - 원소를 탐색하면서 더할지 말지 왼쪽이랑 더할지 오른쪽이랑 더할지 머리가 아프다..
  
   - 생각을 해 보자. 결국 모든 원소의 값이 같아질 때, 원소의 값은 아무리 커도 원래 배열의 모든 원소의 합과 같다.
    ```
    테케 2 2 3을 보면, 결국 [7]이 되는데, 2+2+3이다.
    ```
    - 즉, 원소를 모두 합하고, 합을 같게 나눌 수 있는, 약수를 기준으로 하여 합칠 수 있는지를 판단해야 한다.

3. 구체적인 방법
  - 배열을 순회하여 total을 구한다.
  - total의 약수를 담은 divisors를 구한다. 이때 0을 포함해야 한다.
  - divisors에 있는 divisor가 기준이 된다. 배열의 원소를 순서대로 접근하면서 sum에 합친다. 이후 sum이 divisor만큼 합쳐지는지를 판단하여 탐색을 더 할지 말지 판단
  - 탐색이 올바르게 끝나면(즉 조건에 맞게 잘리면) combined횟수를 비교하여 최솟값을 업데이트 한다. 



# 1차 시도 - 4% 틀림
<details>
<summary> 
  
  ## 원인
  </summary>
  
  원소가 도중에 0인 경우를 생각 못했다 !!!!


내 방법은 sum에 원소를 하나하나 담아가면서 약수와 같은지를 체크하는데, 이때 sum을 비우고 원소를 새로 넣을 때는
combined의 경우가 아니므로 이를 구분했어야 했다. 이때 원소가 sum에 처음 들어오는지 아닌지를 판단하는 방법은 다음과 같았다. 

```
 while(i<size){
                combined += (sum==0 ? 0 : 1); // 현재 sum이 초기화되고 처음 원소가 들어오는 것 인지 아니면 원래 원소가 있던 sum에 다음 원소가 합해지는건지 체크
                sum += list.get(i);
```
근데 이때, 코드 전체를 모르더라도 가정을 해 보자. 만약 list.get(i) = 0이라면?
0 다음에 들어오는 list.get(i+1)은 저 식으로는 sum이 초기화되고 처음 원소가 들어오는 것으로 판단을 한다.

즉, 다음과 같다.
```
arr = [ ... 0, 5, ...] 라 할 때,
sum = []    <- 앞의 원소들의 합이 divisor와 같아서 sum이 0으로 초기화됨
sum = [0]   <- 다음 원소인 0이 들어왔음, 처음 들어오는 원소(이걸 sum ==0 으로 판단)니깐 combined+1 안 함. 
sum = [0]   <- 이때 여기에 다음 원소인 5가 들어오려고 할 때

combined가 보기에는 sum = 0이므로 5가 들어와도 combined + 1 을 하지 않게 된다.
0과 5를 합쳐 combined를 한 번 해야 하는데, 내 로직대로라면 combined를 하지 않는 것으로 인식한다.
왜? sum이 0이니깐 ㅋㅋㅋㅋ  
```

그래서 원소가 0인지도 생각을 했어야 했다.

그래서 원소가 0인지를 판단하고 combined를 더하거나 아니면 이전에 들어온 원소가 0인지를 판단하기에는
코드가 너무 더러워지고 index out of bound error도 생길 수 있다. 그래서 코드를 아예 바꿔보자.

전
```
// 3. 약수를 순회하며 약수만큼 자를 수 있는지 체크한다.
        for(Integer divisor : divisors){
            int i=0, sum=0, combined=0;
            boolean well_partitioned = true;

            while(i<size){
                combined += (sum==0 ? 0 : 1);
                sum += list.get(i);

                if(sum > divisor){
                    well_partitioned = false;
                    break;
                }
                if(sum == divisor){
                    sum = 0;
                }
                i += 1;
            }

            if(well_partitioned){
                min = Math.min(min, combined);
            }
        }
```
후
```
        // 3. 약수를 순회하며 약수만큼 자를 수 있는지 체크한다.
        for(Integer divisor : divisors){
            int i=0, sum=0, combined=0;
            boolean well_partitioned = true;

            while(i<size){
                sum += list.get(i);
                combined += 1; // 그냥 sum이 초기화 된 상태인지를 생각 안하고 들어오면 무조건 더하는걸로 생각

                if(sum > divisor){
                    well_partitioned = false;
                    break;
                }
                if(sum == divisor){
                    sum = 0;
              	    combined -= 1; // 처음에 그냥 들어온 것을 combined로 인식했으므로, 여기서 1을 빼준다. 
                }
                i += 1;
            }

            if(well_partitioned){
                min = Math.min(min, combined);
            }
        }
        return min;
    }
```

</details>


# 코드
```
import java.util.*;
import java.io.*;

public class Main{
    public static void main(String[] args) throws Exception {
        StringBuilder result = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for(int i=0; i<T; i++){
            int size = Integer.parseInt(br.readLine());
            String[] inputs = br.readLine().split(" ");
            List<Integer> list = new ArrayList<>();

            for(int j=0; j<size; j++){
                list.add(Integer.parseInt(inputs[j]));
            }
            result.append(getAnswer(size, list)).append("\n");
        }
        System.out.println(result.toString());
    }

    private static int getAnswer(int size, List<Integer> list){
        int min = Integer.MAX_VALUE;
        int total = 0;
        List<Integer> divisors;

        // 1. 모두 합한다.
        for(int element:list){
            total += element;
        }

        // 2. 약수를 구한다.
        divisors = getDivisorsOf(total);

        for(Integer divisor : divisors){
            int i=0, sum=0, combined=0;
            boolean well_partitioned = true;

            while(i<size){
                sum += list.get(i);
                combined += 1; // 그냥 sum이 초기화 된 상태인지를 생각 안하고 들어오면 무조건 더하는걸로 생각

                if(sum > divisor){
                    well_partitioned = false;
                    break;
                }
                if(sum == divisor){
                    sum = 0;
              	    combined -= 1; // 처음에 그냥 들어온 것을 combined로 인식했으므로, 여기서 1을 빼준다. 
                }
                i += 1;
            }

            if(well_partitioned){
                min = Math.min(min, combined);
            }
        }
        return min;
    }


    private static List<Integer> getDivisorsOf(int number){
        List<Integer> result = new ArrayList<>();
        result.add(0); // number % 0 == error이므로 먼저 넣는다.

        //int end = (int)Math.sqrt(number) + 1;
        int end = number;
        for(int divisor=1; divisor<=end; divisor++){
            if(number % divisor == 0){
                result.add(divisor);
            }
        }
        return result;
    }
}
```

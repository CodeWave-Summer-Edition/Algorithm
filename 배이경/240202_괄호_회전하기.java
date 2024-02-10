import java.util.*;
import java.io.*;

class Solution {
    public static boolean check(String s){
        Stack<Character> stack = new Stack<>();
        boolean flag = true;
        // 괄호문자열 검사
        for(int i=0;i<N;i++){
            char now = s.charAt(i);
            if (now =='(' || now == '{' || now == '['){
                stack.push(now);
            }
            else{
                if (!stack.isEmpty()){
                    if (dic.get(stack.peek()) == now){
                        stack.pop();
                    }
                    else {
                        flag = false;
                        break;
                    }
                }
                else {
                    flag = false;
                    break;
                }
            }
        }
        if (!stack.isEmpty()) flag = false;

        // System.out.println(s+ " " + flag);
        return flag;
    }
    static Map<Character, Character> dic = new HashMap<>(){{
        put('{','}');
        put('(',')');
        put('[',']');
    }};
    static int N;
    public int solution(String s) {
        N = s.length();
        int answer = 0;
        if (check(s)) answer++;
        Deque<Character> queue = new ArrayDeque<>();
        for(int n=0; n < N; n ++){
            queue.offer(s.charAt(n));
        }
        
        for(int n = 0; n < N-1; n ++){
            StringBuilder sb = new StringBuilder();
            char c = queue.pollLast();
            queue.offerFirst(c);
            for (int m = 0; m < N; m++){
                char c1 = queue.poll();
                sb.append(c1);
                queue.offer(c1);
            }
            if (check(sb.toString())) answer++;
        }
        
        


        return answer;
    }
}

import java.util.*;

class Solution {
    public static int function(int K){
        int cnt = 0;
        while(!pq.isEmpty()){
            if (pq.size() <= 1){
                if (pq.peek() < K) return -1;
                else return cnt;
            }
            int first = pq.poll();
            int second = pq.poll();
            // System.out.println(first + " " + second);
            // System.out.println(pq);
            if (first < K || second < K){
                pq.offer(first + second*2);
                cnt++;                
            }
        }
        return cnt;
        
    }
    static PriorityQueue<Integer> pq;
    public int solution(int[] scoville, int K) {
        int answer = -1;
        Arrays.sort(scoville);
        pq = new PriorityQueue<>((o1, o2)->{
            return o1 - o2;
        });
        for(int i=0; i<scoville.length; i++){
            pq.offer(scoville[i]);
        }
        answer = function(K);
        return answer;
    }
}

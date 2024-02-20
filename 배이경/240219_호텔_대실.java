import java.util.*;

class Solution {
    public int solution(String[][] book_time) {
        
        int[][] times = new int[book_time.length][2];
        
        // 분 단위 변환
        for(int i=0; i < book_time.length; i++){
            String start_at[] = book_time[i][0].split(":");
            String end_at[] = book_time[i][1].split(":");
            times[i][0] = Integer.parseInt(start_at[0])*60 + Integer.parseInt(start_at[1]);
            // 퇴실 시간 + 10 분
            times[i][1] = Integer.parseInt(end_at[0])*60 + Integer.parseInt(end_at[1])+10;     
        }
        
        // 입실 시각 기준 오름차순 정렬
        Arrays.sort(times, (o1, o2) -> o1[0] - o2[0]);

        // 방 번호
        int answer = 1;
        
        // 방 번호, 끝나는 시간 저장 맵
        Map<Integer, Integer> dic = new HashMap<>();
        dic.put(answer, times[0][1]);
        for(int i = 1; i < times.length; i ++){
            boolean flag = false;
            
            for (int key : dic.keySet()){
                if (dic.get(key) <= times[i][0]){
                    flag = false;
                    dic.replace(key, times[i][1]);
                    break;
                }
                else flag = true;
            }
            if (flag) {
                answer++;
                dic.put(answer, times[i][1]);
            }
        }
        
        return answer;
    }
}

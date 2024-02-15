import java.util.*;

class Solution {
    // 메뉴 조합 만들기
    public static void dfs(int idx, String order, int N, int cnt, StringBuilder sb){
        if (cnt == N){
            String s = sb.toString();
            dic.put(s, dic.getOrDefault(s,0)+1);
            return;
        }
        for(int i = idx; i < order.length(); i++){
            sb.append(order.charAt(i));
            dfs(i+1, order, N, cnt+1, sb);
            sb.delete(cnt, cnt+1);
        }
    }
    static HashMap<String, Integer> dic;
    public String[] solution(String[] orders, int[] course) {
        
        List<String> answer = new LinkedList<>();
        
        // 문자열 정렬
        for(int i =0; i < orders.length; i++){
            char[] chars = orders[i].toCharArray();
            Arrays.sort(chars);
            orders[i] = String.valueOf(chars);
        }
        
        // 코스 조합만큼 메뉴 조합 만들기
        for(int c = 0; c < course.length; c++){
            dic = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            int max = -1;
            for(int i = 0; i < orders.length; i++){
                if (course[c] <= orders[i].length()) {
                    dfs(0, orders[i], course[c], 0, sb);
                }
            }
            
            for(String key : dic.keySet()){
                max = Math.max(dic.get(key), max);
            }
            
            for(String key : dic.keySet()){
                if (dic.get(key) >= 2 && dic.get(key) == max){
                    answer.add(key);
                }
            }
        }
        
        Collections.sort(answer);
        
        return answer.toArray(new String[answer.size()]);
    }
}

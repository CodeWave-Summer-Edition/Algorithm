import java.util.*;
class Solution {
    public static int bfs(int start){
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(start);
        int cnt = 0;
        
        while(!q.isEmpty()){
            int now = q.poll();
            
            for(int i : adj.get(now)){
                if (!v[i]){
                    v[i] = true;
                    q.offer(i);
                    cnt++;
                }
            }
        }
        // System.out.println("cnt : " + cnt);
        return cnt;
    }
    static List<List<Integer>> adj;
    static boolean[] v;
    public int solution(int n, int[][] wires) {
        int answer = Integer.MAX_VALUE;
        for(int w=0; w<n-1; w++){
            int temp = 0;
            adj = new ArrayList<>();
            for(int i=0; i<=n; i++){
                adj.add(new ArrayList<>());
            }
            for(int x=0; x<n-1; x++){
                adj.get(x+1).add(x+1);
                if(w==x) continue;
                adj.get(wires[x][0]).add(wires[x][1]);
                adj.get(wires[x][1]).add(wires[x][0]);
            }
            // System.out.println(adj);
            v = new boolean[n+1];
            for(int j=1; j<=n; j++){
                if(!v[j]){
                    if (temp ==0) temp = bfs(j);
                    else temp -= bfs(j);
                }
            }
            // System.out.println(Math.abs(temp) + " " + answer);
            answer = Math.min(Math.abs(temp), answer);
        }

        return answer;
    }
}

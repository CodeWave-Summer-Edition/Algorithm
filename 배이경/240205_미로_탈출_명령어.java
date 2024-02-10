import java.util.*;
class Solution {
    public static class Node implements Comparable<Node>{
        int i;
        int j;
        int cnt;
        String s;
        public Node(int i, int j, int cnt, String s){
            this.i = i;
            this.j = j;
            this.cnt = cnt;
            this.s = s;
        }
        @Override
        public int compareTo(Node o){
            return this.s.compareTo(o.s);
        }
    }
    public String solution(int n, int m, int x, int y, int r, int c, int k) {
        List<String> list = new LinkedList<>();
        int[][] direction = {{1,0},{0,-1},{0,1},{-1,0}}; 
        char[] move = {'d','l','r','u'};
        boolean[][][] v = new boolean[n+1][m+1][k+1];
        
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(x,y,0,""));
        v[x][y][0] = true;
        while(!pq.isEmpty()){
            Node now = pq.poll();
            if (now.i == r && now.j == c && now.cnt == k){
                list.add(now.s);
                break;
            }
            if (now.cnt >= k) continue;
            for(int d = 0; d < 4; d++){
                int ni = now.i+direction[d][0];
                int nj = now.j+direction[d][1];
                int ncnt = now.cnt + 1;
                if (0 < ni && ni <= n && 0 < nj && nj <= m && !v[ni][nj][ncnt]){
                    v[ni][nj][now.cnt+1] = true;
                    pq.offer(new Node(ni,nj,ncnt,now.s+move[d]));
                }
            }
        }
        // for(int i =0; i<list.size(); i++){
        //     System.out.println(list.get(i));
        // }
        // Collections.sort(list);
        
        return list.size() >= 1 ? list.get(0) : "impossible";
    }
}

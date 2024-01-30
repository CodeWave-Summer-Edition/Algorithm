import java.util.*;

class Solution {
    public static String convert(int y){
        String str = "";
        if (y==0) str = "1";
        else if (y==1) str = "2";
        else if (y==2) str = "4";
        return str;
    }
    public static void dfs(int n){
        
        int x = n / 3;
        int y = n % 3;
        // System.out.println("x : " + x + ", y : " + y);

        sb.insert(0,convert(y));
        if (x < 1){
            // sb.insert(0,convert(y));
            // System.out.println(sb.toString());
            return;
        }
        dfs(x-1);   
    }
    static StringBuilder sb = new StringBuilder();
    public String solution(int n) {
        dfs(n-1);
        // String answer = sb.toString();
        return sb.toString();
    }
}

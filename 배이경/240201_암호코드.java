import java.io.*;
import java.util.*;
class Main{
    static int DIV = 1000000;
    public static void ftn(String str){
        int[] dp = new int[str.length()];
        dp[0] = 1;
        for (int i = 1; i < str.length(); i++) {
            String s1 = str.substring(i,i+1);
//            System.out.println(s1);
            if (!s1.equals("0")) dp[i] = (dp[i] + dp[i-1])%DIV;

            String s2 = str.substring(i-1,i+1);
//            System.out.println(s2);
            if (10 <= Integer.parseInt(s2) && Integer.parseInt(s2) <= 26){
                dp[i] = (dp[i] + dp[i-2])%DIV;
            }
        }
        System.out.println(dp[str.length()-1]%DIV);
    }
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = '0'+br.readLine();
        ftn(str);
    }
}

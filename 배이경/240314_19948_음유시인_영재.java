import java.io.*;
import java.util.*;

class Main {

    // 알파벳 별 자판을 칠 수 있는 횟수를 저장하기 위한 배열
    static int[] alphabets = new int[26]; // <- 다들 하듯이 알파ㅔㅅ 횟수 저장한거
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // [0] 입력
        String s = br.readLine();
        int remaining = Integer.parseInt(br.readLine()); // 스페이스 바 남은 횟수
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < 26; i++) {
            alphabets[i] = Integer.parseInt(st.nextToken());
        }

        // [1] 첫번째 문자 처리
        // 문자열의 시작은 무조건 문자이기 때문에 첫번째를 무조건 넣음 <= 나는 이거 일부러 뺴놓은게, 나중에 포문 돌면서 현재값, 직전값 찾아오기 쉬우려고 0 번째 인덱스는 무조건 추가했음

        StringBuilder sb = new StringBuilder();
        // 스페이스 바 횟수가 0이거나, 더이상 칠 수 있는 알파벳 수가 남이있지 않을 경우 반복문을 탈출하기 위한 flag <= 현실언니처럼 flag 사용해서 반복문 탈출하기 위한 장치 마련함!
        boolean flag = false;
        char start = s.charAt(0);
        flag = isFlag(sb, flag, start);
        // 그래서 일단 0번째 인덱스로 string builder 에 추가해줬고

        // [2] 두번째 문자 이후로 처리는 로직은 똑같아
        for (int i = 1; i < s.length(); i++) {
            // 현재, 직전 값 찾기
            // 직전 문자열 찾기 쉬우려고 1부터 시작
            char now = s.charAt(i);
            char before = s.charAt(i-1);

            // flag이면 조건을 만족하지 못하므로 break
            // 사실 now, before 가져오기 전에 해도 되는데 디버깅하려고 밑에 넣음
            // 그러면 그 다음에 여기서 걸리겠지?
            if (flag) break;

            // now == before 일 경우 더 안눌러도 되니까 넘어감
            if (now == before) continue;


            // 요것도 제목에 들어가는 케이스라서 분리한 메소드 사용!
            if (before == ' '){
                flag = isFlag(sb, flag, now);
                continue;
            }

            // 이거는 현재가 ' ' 인 경우에 스페이스바 남은 횟수에서 차감하는 것이고, 0보다 작아지면 flag를 true로 바꿨어
            if (now == ' '){
                remaining--;
                if (remaining < 0) flag = true;
            }
            // 만약 스페이스바가 아니라면
            else {
                // 소문자일 경우에
                if ((int) now >= 97){
                    // alphabets 배열에서 자신에게 해당하는 인덱스의 값이 0보다 커야지만 입력이 가능하니까 큰 경우, 아닌 경우로 구분해서
                    if (alphabets[(int) now - 97] > 0) {
                        // 0보다 크면 1회 차감
                        alphabets[(int) now - 97]--;
                    }
                    // 아니면 플래그를 true로 변경
                    else flag = true;
                }
                // 대문자도 로직은 동일
                else {
                    if (alphabets[(int) now - 65] > 0) alphabets[(int) now - 65]--;
                    else flag = true;
                }
            }
        }

        // 그래서 최종적으로 플래그만 봤을 때 true이면 어차피 조건 만족을 못한거니까 -1 찍고, 아니면 sb에 담아두었던 애들 혹시 모르니까 uppercase 한번 더 해줫어!
        bw.write(flag ? "-1" : sb.toString().toUpperCase());
        bw.flush();
        bw.close();

        // 끗!
    }

    // 제목에 넣는 경우 flag 여부를 판단하기 위해 공통적으로 사용하는 메소드 <= 요게 0번쨰 인덱스, 직전 문자가 ' ' 인 경우 둘 다 해당하잖아
    // 그래서 이걸 공통 메소드로 분리를 했고
    private static boolean isFlag(StringBuilder sb, boolean flag, char now) {
        // 소문자일 경우 -> 재원이처럼 now 가 97 이상/ 이하인 경우로 구분했음
        if ((int) now >= 97) {
            sb.append(now - 32); // <= 대문자로 넣기 위한 것!
            alphabets[(int) now - 97] -= 2; // <= 나중에 제목도 치고 지금도 쳐야하니까 두 번 뺐어 한꺼번에
            if (alphabets[now - 97] < 0) flag = true; // <= 그래서 만약 남은 횟수가 0 미만이면 플래그를 true로 변경
        }
        // 대문자일 경우 로직은 동일!
        else {
            sb.append(now);
            alphabets[(int) now - 65] -= 2;
            if (alphabets[now - 65] < 0) flag = true;
        }
        return flag;
    }
}

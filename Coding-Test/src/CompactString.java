import java.util.*;

public class CompactString {

    public static int solution(String s) {
        int n = s.length();
        int answer = n;

        for (int chunkSize = 1; chunkSize <= n / 2; chunkSize++) {
            answer = Math.min(answer, compressedLength(s, chunkSize));
        }

        return answer;
    }

    private static int compressedLength(String s, int chunkSize) {
        int n = s.length();

        String prev = s.substring(0, chunkSize);
        int count = 1;
        int length = 0;

        for (int i = chunkSize; i < n; i += chunkSize) {
            String cur = s.substring(i, Math.min(i + chunkSize, n));

            if (prev.equals(cur)) {
                count++;
            } else {
                length += prev.length();
                if (count > 1) length += digits(count);

                prev = cur;
                count = 1;
            }
        }

        // 마지막 덩어리 처리
        length += prev.length();
        if (count > 1) length += digits(count);

        return length;
    }

    private static int digits(int x) {
        return String.valueOf(x).length();
    }
}

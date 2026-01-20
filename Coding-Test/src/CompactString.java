import java.util.*;

public class CompactString {
    public static int solution(String s) {
        int max_chunkSize = s.length() / 2;
        int min_length = s.length();

        for (int chunkSize = 1; chunkSize <= max_chunkSize; chunkSize++) {
            String[] splited_string = split(s, chunkSize);
            int compacted_length = compact_length(splited_string);
            if (compacted_length < min_length) min_length = compacted_length;
        }

        return min_length;
    }

    // 문자열을 단위에 맞추어 쪼개여 문자열 배열로 반환
    public static String[] split(String s, Integer chunkSize) {
        int array_size;
        if (s.length() % chunkSize == 0) array_size = s.length() / chunkSize;
        else array_size = s.length() / chunkSize + 1;
        String[] splited_string = new String[array_size];

        int index = 0;
        for (int i = 0; i < s.length(); i += chunkSize) {
            splited_string[index++] = s.substring(i, Math.min(i + chunkSize, s.length()));
        }

        return splited_string;
    }

    // 문자열 배열을 받아 압축을 진행하여 압축된 길이값 반환
    public static int compact_length(String[] splited_string) {
        String prev = splited_string[0];
        int count = 1;
        int length = 0;

        for (int i = 1; i < splited_string.length; i++) {
            String cur = splited_string[i];

            // 연속될 경우 계산 X
            if (prev.equals(cur)) {
                count++;
            }
            // 연속되지 않을 경우 계산 O
            else {
                // prev 덩어리 길이 반영
                length += prev.length();
                if (count > 1) length += String.valueOf(count).length();

                prev = cur;
                count = 1;
            }
        }

        // 마지막 덩어리 처리
        length += prev.length();
        if (count > 1) length += String.valueOf(count).length();

        return length;
    }
}

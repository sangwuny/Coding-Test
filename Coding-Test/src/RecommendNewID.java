import java.util.*;

public class RecommendNewID {
    public String solution(String new_id) {

        String id = new_id.toLowerCase()                          // 1단계
                .replaceAll("[^a-z0-9-_.]", "")   // 2단계
                .replaceAll("\\.{2,}", ".")       // 3단계
                .replaceAll("^\\.|\\.$", "");     // 4단계

        // 5단계
        if (id.isEmpty()) id = "a";

        // 6단계
        if (id.length() >= 16) {
            id = id.substring(0, 15);
            if (id.endsWith(".")) {
                id = id.substring(0, id.length() - 1);
            }
        }

        // 7단계
        if (id.length() <= 2) {
            char last = id.charAt(id.length() - 1);
            StringBuilder sb = new StringBuilder(id);
            while (sb.length() < 3) sb.append(last);
            id = sb.toString();
        }

        return id;
    }
}

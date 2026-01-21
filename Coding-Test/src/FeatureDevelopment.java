import java.util.*;

public class FeatureDevelopment {
    public int[] solution(int[] progresses, int[] speeds) {
        List<Integer> answer = new ArrayList<>();
        Deque<Integer> q = new ArrayDeque<>();

        for (int i = 0; i < progresses.length; i++) {
            int left = 100 - progresses[i];
            q.offer((left % speeds[i] == 0) ? (left / speeds[i]) : (left / speeds[i] + 1));
        }

        int cur;
        int next = -1;
        while (!q.isEmpty()) {
            cur = q.poll();
            int count = 1;

            while (!q.isEmpty() && cur >= q.peekFirst()) {
                next = q.poll();
                count++;
            }

            answer.add(count);
        }

        return answer.stream().mapToInt(Integer::intValue).toArray();
    }
}


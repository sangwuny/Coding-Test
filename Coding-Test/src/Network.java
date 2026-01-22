import java.util.*;

public class Network {
    // 네트워크가 인접행렬 형태로 주어짐
    public int solution(int n, int[][] computers) {
        int count = 0;
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < n; i++) {
            if (!visited.contains(i)) {
                bfs(i, visited, computers);
                count++;
            }
        }

        return count;
    }

    public static void bfs(int startVertex, Set<Integer> visited, int[][] computers) {
        // 시작점 예약
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(startVertex);
        visited.add(startVertex);

        // queue가 비어있을 때까지 반복
        while (!queue.isEmpty()) {
            // 현재 노드 방문
            int curVertex = queue.poll();

            // 다음 노드 예약
            for (int nextVertex = 0; nextVertex < computers[curVertex].length; nextVertex++) {
                if (nextVertex != curVertex
                        && !visited.contains(nextVertex)
                        && computers[curVertex][nextVertex] == 1) {
                    queue.offer(nextVertex);
                    visited.add(nextVertex);
                }
            }
        }
    }
}
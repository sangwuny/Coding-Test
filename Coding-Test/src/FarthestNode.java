import java.util.*;

public class FarthestNode {
    public int solution(int n, int[][] edge) {
        return bfs(n, edge, 1);
    }

    public int bfs(int n, int[][] edge, int startVertex) {
        // 2중 List를 통해 graph 정의
        List<List<Integer>> graph = new ArrayList<>();

        // 2중 List는 행을 먼저 만들어야 함, index 0은 사용하지 않을 예정
        for (int i = 0; i <= n; i++) {
            graph.add(new ArrayList<>());
        }

        // 열 추가는 graph.get().add() 로 해야함
        for (int[] e : edge) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        Map<Integer, Integer> visited = new HashMap<>();
        Deque<Integer> queue = new ArrayDeque<>();

        queue.offer(startVertex);
        visited.put(startVertex, 0);

        while (!queue.isEmpty()) {
            int curVertex = queue.poll();

            for (int nextVertex : graph.get(curVertex)) {
                if (!visited.containsKey(nextVertex)) {
                    queue.offer(nextVertex);
                    visited.put(nextVertex, visited.get(curVertex) + 1);
                }
            }
        }

        int maxDistance = Collections.max(visited.values());
        long count = visited.values().stream().filter(v -> v == maxDistance).count();

        return (int) count;
    }
}

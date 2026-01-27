import java.util.*;

/*
이분 그래프(Bipartite Graph): 그래프의 모든 노드를 두 개의 독립된 집합 A와 B로 나누었을 때,
그래프의 모든 간선이 집합 A에 속한 노드와 집합 B에 속한 노드만을 연결한다면 이 그래프를 이분 그래프라고 부른다.
즉, 내가 1로 색칠 되었고 나와 인접한 노드가 전부 2로 색칠 되어 있다면 이분 그래프를 만족. 만약 내 인접 노드에 나와 같은 색깔이 있다면 X.
 */

public class IsGraphBipartite {
    public boolean isBipartite(int[][] graph) {
        int n = graph.length;

        // 0: 미방문, 1: Set A, -1: Set B
        int[] colors = new int[n];

        for (int i = 0; i < n; i++) {
            // 이미 방문(색칠)된 노드는 건너뜀 (비연결 그래프 대응)
            if (colors[i] != 0) continue;

            // BFS 시작
            Deque<Integer> queue = new ArrayDeque<>();
            queue.offer(i);
            colors[i] = 1; // 시작 노드를 Set A(1)로 색칠

            while (!queue.isEmpty()) {
                int cur = queue.poll();

                for (int next : graph[cur]) {
                    if (colors[next] == 0) { // 방문하지 않은 인접 노드라면
                        colors[next] = -colors[cur]; // 현재 노드와 반대 색으로 색칠
                        queue.offer(next);
                    } else if (colors[next] == colors[cur]) {
                        // 이미 방문했는데, 나랑 색이 같다면 이분 그래프 X
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
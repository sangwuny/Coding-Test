import java.util.*;

// Embulance와 동일한 문제. Node를 사용하는 방법으로 재풀이 해봄.

public class ShortestPathInBinaryMatrix {
    private static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1};
    private static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};

    // 내부 클래스로 Node 정의
    private static class Node {
        int r, c, dist;

        Node(int r, int c, int dist) {
            this.r = r;
            this.c = c;
            this.dist = dist;
        }
    }

    public int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0) return -1;

        int m = grid.length;
        int n = grid[0].length;

        if (grid[0][0] == 1 || grid[m - 1][n - 1] == 1) return -1;

        return bfs(grid, m, n);
    }

    private int bfs(int[][] grid, int m, int n) {
        Queue<Node> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[m][n];

        // 시작점 생성 (거리 1부터 시작)
        queue.offer(new Node(0, 0, 1));
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Node cur = queue.poll();

            // 목적지 도달 시 해당 노드가 가진 거리를 즉시 반환
            if (cur.r == m - 1 && cur.c == n - 1) {
                return cur.dist;
            }

            for (int i = 0; i < 8; i++) {
                int nr = cur.r + DR[i];
                int nc = cur.c + DC[i];

                if (isValid(nr, nc, m, n) && grid[nr][nc] == 0 && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    // 다음 노드 생성 시 현재 거리 + 1
                    queue.offer(new Node(nr, nc, cur.dist + 1));
                }
            }
        }

        return -1;
    }

    private boolean isValid(int r, int c, int m, int n) {
        return r >= 0 && r < m && c >= 0 && c < n;
    }
}
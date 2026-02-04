import java.util.*;

public class TreasureMap {
    public static void main(String[] args) {
        int n = 4, m = 4;
        int[][] hole = {
                {2, 3}, {3, 3}
        };
//        int n = 5, m = 4;
//        int[][] hole = {
//                {1, 4},
//                {2, 1},
//                {2, 2},
//                {2, 3},
//                {2, 4},
//                {3, 3},
//                {4, 1},
//                {4, 3},
//                {5, 3}
//        };


        System.out.println(new Solution().solution(n, m, hole));
    }
}

class Solution {
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    static class Node {
        int r, c, dist, used; // used: 0 = 신발 미사용, 1 = 신발 사용
        Node(int r, int c, int dist, int used) {
            this.r = r;
            this.c = c;
            this.dist = dist;
            this.used = used;
        }
    }

    public int solution(int n, int m, int[][] hole) {
        return bfs(n, m, hole);
    }

    private int bfs(int n, int m, int[][] hole) {
        // 함정 표시
        boolean[][] blocked = new boolean[n + 1][m + 1];
        for (int[] h : hole) {
            blocked[h[0]][h[1]] = true;
        }

        // visited[r][c][used] : (r,c)에 신발 used 상태로 방문했는가
        boolean[][][] visited = new boolean[n + 1][m + 1][2];
        Queue<Node> q = new ArrayDeque<>();

        // 시작
        visited[1][1][0] = true;
        q.offer(new Node(1, 1, 0, 0));

        while (!q.isEmpty()) {
            Node cur = q.poll();

            // 목표 도착
            if (cur.r == n && cur.c == m) return cur.dist;

            for (int i = 0; i < 4; i++) {
                // 1칸 걷기
                int nr = cur.r + dr[i];
                int nc = cur.c + dc[i];

                if (inRange(nr, nc, n, m) && !blocked[nr][nc] && !visited[nr][nc][cur.used]) {
                    visited[nr][nc][cur.used] = true;
                    q.offer(new Node(nr, nc, cur.dist + 1, cur.used));
                }

                // 2칸 점프 (신발 미사용일 때만)
                if (cur.used == 0) {
                    int nr2 = cur.r + 2 * dr[i];
                    int nc2 = cur.c + 2 * dc[i];

                    // 중간칸(nr,nc)은 함정이어도 "넘을 수 있음"
                    // 착지칸만 범위/함정 체크
                    if (inRange(nr2, nc2, n, m) && !blocked[nr2][nc2] && !visited[nr2][nc2][1]) {
                        visited[nr2][nc2][1] = true;
                        q.offer(new Node(nr2, nc2, cur.dist + 1, 1));
                    }
                }
            }
        }

        return -1;
    }

    private boolean inRange(int r, int c, int n, int m) {
        return r >= 1 && r <= n && c >= 1 && c <= m;
    }
}

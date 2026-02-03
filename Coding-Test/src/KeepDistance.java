import java.util.*;

public class KeepDistance {
    public static void main(String[] args) {
         String[][] places = {
                {"POOOP", "OXXOX", "OPXPX", "OOXOX", "POXXP"},
                {"POOPX", "OXPXP", "PXXXO", "OXXXO", "OOOPP"},
                {"PXOPX", "OXOXP", "OXPOX", "OXXOP", "PXPOX"},
                {"OOOXX", "XOOOX", "OOOXX", "OXOOX", "OOOOO"},
                {"PXPXP", "XPXPX", "PXPXP", "XPXPX", "PXPXP"}
        };

        int[] result = Solution.solution(places);

        System.out.println(Arrays.toString(result)); // [1, 0, 1, 1, 1]
    }

    private static class Solution {
        static final int SIZE = 5;
        static final int[] dr = {1, -1, 0, 0};
        static final int[] dc = {0, 0, 1, -1};

        private static int[] solution(String[][] places) {
            int[] answer = new int[places.length];

            for (int i = 0; i < places.length; i++) {
                answer[i] = checkPlace(places[i]) ? 1 : 0;
            }

            return answer;
        }

        private static boolean checkPlace(String[] place) {
            for (int r = 0; r < SIZE; r++) {
                for (int c = 0; c < SIZE; c++) {
                    if (place[r].charAt(c) == 'P') {
                        if (!bfsFromPerson(place, r, c)) return false;
                    }
                }
            }
            return true;
        }

        // (sr, sc) 사람 기준으로 맨해튼 거리 2 이내 위반이 없으면 true
        private static boolean bfsFromPerson(String[] place, int sr, int sc) {
            boolean[][] visited = new boolean[SIZE][SIZE];
            Queue<int[]> q = new ArrayDeque<>();

            q.add(new int[]{sr, sc, 0}); // row, col, dist
            visited[sr][sc] = true;

            while (!q.isEmpty()) {
                int[] cur = q.poll();
                int r = cur[0], c = cur[1], dist = cur[2];

                for (int d = 0; d < 4; d++) {
                    int nr = r + dr[d];
                    int nc = c + dc[d];
                    int nd = dist + 1;

                    if (nr < 0 || nc < 0 || nr >= SIZE || nc >= SIZE) continue;
                    if (visited[nr][nc]) continue;
                    if (nd > 2) continue;

                    char ch = place[nr].charAt(nc);

                    if (ch == 'X') continue;          // 파티션이면 막힘
                    if (ch == 'P') return false;      // 거리 2 이내 다른 사람 발견 → 위반

                    // 'O'인 경우만 계속 탐색
                    visited[nr][nc] = true;
                    q.add(new int[]{nr, nc, nd});
                }
            }
            return true;
        }
    }
}
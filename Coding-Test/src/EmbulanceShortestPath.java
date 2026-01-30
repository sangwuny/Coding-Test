import java.util.*;

/*
문제 파악: (0,0) -> (m-1,n-1) 까지의 최단 경로를 구하라
상하좌우 대각선으로 이동 가능하며 0인 경로만 이동 가능
가장 짧은 경로를 반환하며 불가능할 경우 -1 반환
 */

public class EmbulanceShortestPath {
    public int solution(int[][] city) {
        return bfs(city);
    }

    public static int[] dr = {-1, -1, -1, 0, 0, 1, 1, 1}; // 상하
    public static int[] dc = {-1, 0, 1, -1, 1, -1, 0, 1}; // 좌우


    public int bfs(int[][] city) {
        // 시작점이나 목적지가 벽(1)이면 탐색할 필요도 없이 -1
        if (city[0][0] == 1 || city[city.length - 1][city[0].length - 1] == 1) return -1;


        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[city.length][city[0].length];
        int count = 1;

        queue.offer(new int[]{0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int s = 0; s < size; s++) {
                int[] cur = queue.remove();
                int cr = cur[0];
                int cc = cur[1];

                if (cr == city.length - 1 && cc == city[0].length - 1) return count;

                for (int i = 0; i < 8; i++) {
                    int nr = cr + dr[i];
                    int nc = cc + dc[i];

                    if (nr >= 0 && nr < city.length && nc >= 0 && nc < city[0].length && city[nr][nc] == 0 && !visited[nr][nc]) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }

            }

            count++;
        }

        return -1;
    }
}

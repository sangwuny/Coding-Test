import java.util.*;

/*
문제 파악: m*n 배열에 땅은 1, 물은 0으로 표현된 지도가 존재함. 이때 땅덩어리 개수를 반환하라.

m == grid.length
n == grid[i].length

접근 방법: 행렬을 노드와 간선 형태로 구현된 graph로 변환하여 bfs 혹은 dfs를 하여 몇 개의 graph가 존재하는지 파악
 */

public class NumberOfIslands {
    public int numIslands(char[][] grid) {
        int count = 0;

        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == '1') {
                    count++;
                    bfs(row, col, grid);
                }
            }
        }

        return count;
    }

    public void bfs(int row, int col, char[][] grid) {
        Queue<int[]> queue = new ArrayDeque<>();

        queue.offer(new int[]{row, col});
        grid[row][col] = '0';

        int[] dr = {-1, 1, 0, 0}; // 상 하
        int[] dc = {0, 0, -1, 1}; // 좌 우

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();

            for (int i = 0; i < 4; i++) {
                int nr = cur[0] + dr[i];
                int nc = cur[1] + dc[i];

                if (nr >= 0 && nr < grid.length && nc >= 0 && nc < grid[0].length) {
                    if (grid[nr][nc] == '1') {
                        grid[nr][nc] = '0';
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }
        }
    }
}

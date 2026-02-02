import java.util.*;

/*
문제파악: 시작지점(S)에서 레버(L)에 도달한 후 출구(E)에 도달하라. 그리고 그때 까지 걸린 최소 시간을 반환하라.

제한사항:
미로에서 한 칸을 이동하는데 1초가 걸린다.
5 ≤ maps의 길이 ≤ 100
5 ≤ maps[i]의 길이 ≤ 100
maps[i]는 다음 5개의 문자들로만 이루어져 있습니다.
S : 시작 지점
E : 출구
L : 레버
O : 통로
X : 벽
시작 지점과 출구, 레버는 항상 다른 곳에 존재하며 한 개씩만 존재합니다.
출구는 레버가 당겨지지 않아도 지나갈 수 있으며, 모든 통로, 출구, 레버, 시작점은 여러 번 지나갈 수 있습니다.


 */

public class MazeEscape {
    public static int solution(String[] maps) {
        int[][] find = findSLE(maps);
        int[] start = find[0];
        int[] lever = find[1];
        int[] exit = find[2];
        int distance = 0;

        int d1 = bfs(start, lever, maps);
        if (d1 == -1) return -1;
        distance += d1;

        int d2 = bfs(lever, exit, maps);
        if (d2 == -1) return -1;
        distance += d2;

        return distance;
    }

    public static int[][] findSLE(String[] maps) {
        int[][] find = new int[3][2];

        for (int r = 0; r < maps.length; r++) {
            for (int c = 0; c < maps[r].length(); c++) {
                if (maps[r].charAt(c) == 'S') {
                    find[0][0] = r;
                    find[0][1] = c;
                }
                if (maps[r].charAt(c) == 'L') {
                    find[1][0] = r;
                    find[1][1] = c;
                }
                if (maps[r].charAt(c) == 'E') {
                    find[2][0] = r;
                    find[2][1] = c;
                }
            }
        }

        return find;
    }

    public static int[] dr = {-1, 1, 0, 0}; // 상하
    public static int[] dc = {0, 0, -1, 1}; // 좌우

    public static int bfs(int[] startVertex, int[] endVertex, String[] maps) {
        Queue<int[]> queue = new ArrayDeque<>();
        boolean[][] visited = new boolean[maps.length][maps[0].length()];
        int count = 0;
        int maxRow = maps.length - 1, maxCol = maps[0].length() - 1;

        queue.offer(startVertex);
        visited[startVertex[0]][startVertex[1]] = true;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                int r = cur[0];
                int c = cur[1];

                if (endVertex[0] == r && endVertex[1] == c) return count;

                for (int j = 0; j < 4; j++) {
                    int nr = cur[0] + dr[j];
                    int nc = cur[1] + dc[j];

                    if (isValid(nr, nc, maxRow, maxCol, visited, maps)) {
                        visited[nr][nc] = true;
                        queue.offer(new int[]{nr, nc});
                    }
                }
            }

            count++;
        }

        return -1;
    }

    public static boolean isValid(int r, int c, int maxRow, int maxCol, boolean[][] visited, String[] maps) {
        return (r >= 0 && r <= maxRow && c >= 0 && c <= maxCol)
                && !visited[r][c]
                && (maps[r].charAt(c) != 'X');
    }

    public static void main(String[] args) {
        String[] maps = {"SOOOL", "XXXXO", "OOOOO", "OXXXX", "OOOOE"};
        solution(maps);
    }
}

/* 과거 풀이
import java.util.*;

class Solution {
    static int[] dr = {-1, 1, 0, 0};  // 상, 하
    static int[] dc = {0, 0, -1, 1};  // 좌, 우
    static int rowLength;
    static int colLength;

    public static boolean isValid(int r, int c) {
        return (0 <= r) && (r < rowLength) && (0 <= c) && (c < colLength);
    }

    static int bfs(int[] from, int[] to, String[] maps) {
        boolean[][] visited = new boolean[rowLength][colLength];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{from[0], from[1], 0});
        visited[from[0]][from[1]] = true;

        while (!queue.isEmpty()) {
            int[] cur = queue.poll();
            int curRow = cur[0];
            int curCol = cur[1];
            int curDist = cur[2];

            if (curRow == to[0] && curCol == to[1]) return curDist;

            for (int i = 0; i < 4; i++) {
                int nextRow = curRow + dr[i];
                int nextCol = curCol + dc[i];

                if (isValid(nextRow, nextCol)) {
                    if (!visited[nextRow][nextCol] && maps[nextRow].charAt(nextCol) != 'X') {
                        queue.offer(new int[]{nextRow, nextCol, curDist + 1});
                        visited[nextRow][nextCol] = true;
                    }
                }
            }
        }

        return -1; // 도달 불가
    }

    static int solution(String[] maps) {
        rowLength = maps.length;
        colLength = maps[0].length();

        int[] start_index = new int[]{-1, -1};
        int[] lever_index = new int[]{-1, -1};
        int[] exit_index = new int[]{-1, -1};

        for (int i = 0; i < maps.length; i++) {
            int s = maps[i].indexOf("S");
            int l = maps[i].indexOf("L");
            int e = maps[i].indexOf("E");

            if (s != -1) start_index = new int[]{i, s};
            if (l != -1) lever_index = new int[]{i, l};
            if (e != -1) exit_index = new int[]{i, e};
        }

        int start_to_lever = bfs(start_index, lever_index, maps);
        if (start_to_lever == -1) return -1;

        int lever_to_exit = bfs(lever_index, exit_index, maps);
        if (lever_to_exit == -1) return -1;

        return start_to_lever + lever_to_exit;
    }
}
 */
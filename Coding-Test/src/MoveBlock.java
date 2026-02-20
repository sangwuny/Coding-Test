/*
문제 파악:
(1,1),(1,2)에 위치한 크기 2인 로봇의 아무 날개가 (N,N)까지 도달하기 까지에 걸리는 시간을 구해야 한다.
이때 로봇은 상하좌우로 움직일 수 있고 조건부 회전이 가능하다.
회전 조건은 로봇의 한 축을 기준으로 90도 회전 가능하며 이때 회전 경로에 장애물이 있을 경우 회전이 불가능하다.
보드에는 빈칸 0, 장애물 1로 표시되어 있다.
 */

import java.util.*;

public class MoveBlock {
    static int N;
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};

    public int solution(int[][] board) {
        N = board.length;
        return bfs(board);
    }

    static class Robot {
        int x1, y1, x2, y2, time;

        Robot(int x1, int y1, int x2, int y2, int time) {
            // 항상 좌표를 정렬하여 저장 (좌상단 -> 우하단 순서)
            if (x1 < x2 || (x1 == x2 && y1 < y2)) {
                this.x1 = x1; this.y1 = y1;
                this.x2 = x2; this.y2 = y2;
            } else {
                this.x1 = x2; this.y1 = y2;
                this.x2 = x1; this.y2 = y1;
            }
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Robot)) return false;
            Robot robot = (Robot) o;
            return x1 == robot.x1 && y1 == robot.y1 && x2 == robot.x2 && y2 == robot.y2;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x1, y1, x2, y2);
        }
    }

    public int bfs(int[][] board) {
        Queue<Robot> q = new LinkedList<>();
        Set<Robot> visited = new HashSet<>();

        Robot start = new Robot(0, 0, 0, 1, 0);
        q.offer(start);
        visited.add(start);

        while (!q.isEmpty()) {
            Robot cur = q.poll();

            // 목표 도달 확인 (0-indexed 이므로 N-1)
            if ((cur.x1 == N - 1 && cur.y1 == N - 1) || (cur.x2 == N - 1 && cur.y2 == N - 1)) {
                return cur.time;
            }

            for (Robot next : getNextPositions(cur, board)) {
                if (!visited.contains(next)) {
                    visited.add(next);
                    q.offer(next);
                }
            }
        }
        return 0;
    }

    private List<Robot> getNextPositions(Robot cur, int[][] board) {
        List<Robot> nextList = new ArrayList<>();

        // 1. 평행 이동
        for (int i = 0; i < 4; i++) {
            int nx1 = cur.x1 + dr[i], ny1 = cur.y1 + dc[i];
            int nx2 = cur.x2 + dr[i], ny2 = cur.y2 + dc[i];

            if (isValid(nx1, ny1, nx2, ny2, board)) {
                nextList.add(new Robot(nx1, ny1, nx2, ny2, cur.time + 1));
            }
        }

        // 2. 회전
        if (cur.x1 == cur.x2) { // 가로 방향일 때
            for (int i : new int[]{-1, 1}) { // 위, 아래 확인
                if (isValid(cur.x1 + i, cur.y1, cur.x2 + i, cur.y2, board)) {
                    nextList.add(new Robot(cur.x1, cur.y1, cur.x1 + i, cur.y1, cur.time + 1));
                    nextList.add(new Robot(cur.x2, cur.y2, cur.x2 + i, cur.y2, cur.time + 1));
                }
            }
        } else { // 세로 방향일 때
            for (int i : new int[]{-1, 1}) { // 왼쪽, 오른쪽 확인
                if (isValid(cur.x1, cur.y1 + i, cur.x2, cur.y2 + i, board)) {
                    nextList.add(new Robot(cur.x1, cur.y1, cur.x1, cur.y1 + i, cur.time + 1));
                    nextList.add(new Robot(cur.x2, cur.y2, cur.x2, cur.y2 + i, cur.time + 1));
                }
            }
        }

        return nextList;
    }

    public boolean isValid(int x1, int y1, int x2, int y2, int[][] board) {
        return (x1 >= 0 && x1 < N && y1 >= 0 && y1 < N &&
                x2 >= 0 && x2 < N && y2 >= 0 && y2 < N &&
                board[x1][y1] == 0 && board[x2][y2] == 0);
    }
}
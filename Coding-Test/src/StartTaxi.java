import java.io.*;
import java.util.*;

/*
  문제 파악:
  1. 목표: M명의 승객을 모두 목적지로 이동시킨 후 남은 연료량 출력
  2. 핵심 규칙
    - 연료 소모: 이동 시 1칸당 1 소모
    - 연료 충전: 승객 운송 성공 시, 해당 주행에서 소모한 연료의 2배 충전(승객을 데리러 갈 떄 까지 소모한 연로는 충전 X)
    - 실패 조건:
    - 이동 중 연료가 0이 됨 (단, 목적지 도착과 동시에 0이 되는 건 성공)
    - 모든 승객을 태울 수 없거나 목적지로 갈 수 없는 경우
  3. 승객 선택 우선순위 (최단거리 BFS 활용)
    - 현재 택시 위치에서 '최단 거리'가 가장 짧은 승객
    - 거리 동일 시: '행(Row) 번호'가 가장 작은 승객
    - 행 번호 동일 시: '열(Column) 번호'가 가장 작은 승객
  4. 데이터 구조
    - Map: N x N 격자 (0: 빈칸, 1: 벽)
    - Taxi: 현재 위치(r, c), 남은 연료
    - Passenger: 출발지(sr, sc), 목적지(er, ec), 완료 여부
  5. Output: 최종 남은 연료량 (불가능 시 -1)

  접근 방법:
  1. 택시 위치에서 BFS탐색을 시작하여 가장 가까운 승객 탐지 -> 목적지 이동
  2. 위를 모든 승객을 전부 태울때 까지 반복
 */

public class StartTaxi {
    static int N, M, Fuel;
    static int[][] map;
    static Taxi taxi;
    static List<Passenger> passengers;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    static class Taxi {
        int r, c;

        Taxi(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Passenger implements Comparable<Passenger> {
        int id, sr, sc, er, ec, dist;

        Passenger(int id, int sr, int sc, int er, int ec) {
            this.id = id;
            this.sr = sr;
            this.sc = sc;
            this.er = er;
            this.ec = ec;
        }

        // 우선순위: 1. 거리 짧은 순 -> 2. 행 작은 순 -> 3. 열 작은 순
        @Override
        public int compareTo(Passenger o) {
            if (this.dist != o.dist) return Integer.compare(this.dist, o.dist);
            if (this.sr != o.sr) return Integer.compare(this.sr, o.sr);
            return Integer.compare(this.sc, o.sc);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        Fuel = Integer.parseInt(st.nextToken());

        map = new int[N + 1][N + 1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 1; j <= N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        st = new StringTokenizer(br.readLine());
        taxi = new Taxi(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));

        passengers = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            passengers.add(new Passenger(i, Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
                    Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken())));
        }

        System.out.println(solve());
    }

    static int solve() {
        for (int i = 0; i < M; i++) {
            // 1. 가장 가까운 승객 찾기
            Passenger target = findNearestPassenger();
            if (target == null || Fuel < target.dist) return -1;

            // 승객 태우러 이동
            Fuel -= target.dist;
            taxi.r = target.sr;
            taxi.c = target.sc;

            // 2. 목적지로 이동
            int distToDest = bfsToDest(target);
            if (distToDest == -1 || Fuel < distToDest) return -1;

            // 목적지 도착 및 연료 충전
            Fuel += distToDest; // 이동 시 뺀 걸 다시 더하고 보너스로 한 번 더 더하는 것과 같음 (-d + 2d = +d)
            taxi.r = target.er;
            taxi.c = target.ec;
            passengers.remove(target);
        }
        return Fuel;
    }

    // 승객까지의 거리 계산 및 최적 승객 선별
    static Passenger findNearestPassenger() {
        int[][] distMap = bfs(taxi.r, taxi.c);
        PriorityQueue<Passenger> pq = new PriorityQueue<>();

        for (Passenger p : passengers) {
            int d = distMap[p.sr][p.sc];
            if (d != -1) {
                p.dist = d;
                pq.add(p);
            }
        }
        return pq.peek();
    }

    // 승객 위치에서 목적지까지의 거리 계산
    static int bfsToDest(Passenger p) {
        int[][] distMap = bfs(p.sr, p.sc);
        return distMap[p.er][p.ec];
    }

    // 범용 BFS: (str, stc)로부터 모든 칸까지의 거리 계산
    static int[][] bfs(int str, int stc) {
        int[][] dist = new int[N + 1][N + 1];
        for (int[] row : dist) Arrays.fill(row, -1);

        Queue<int[]> q = new LinkedList<>();
        q.add(new int[]{str, stc});
        dist[str][stc] = 0;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            for (int i = 0; i < 4; i++) {
                int nr = curr[0] + dr[i];
                int nc = curr[1] + dc[i];

                if (nr < 1 || nc < 1 || nr > N || nc > N || map[nr][nc] == 1 || dist[nr][nc] != -1) continue;

                dist[nr][nc] = dist[curr[0]][curr[1]] + 1;
                q.add(new int[]{nr, nc});
            }
        }
        return dist;
    }
}
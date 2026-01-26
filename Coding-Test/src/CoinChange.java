import java.util.*;

public class CoinChange {
    public int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;

        int[] sorted_coins = Arrays.stream(coins).sorted().toArray();

        return bfs(coins, amount);

//        // DP를 사용하는 방법이 좀 더 정석
//        // dp[x] = x원을 만드는 최소 동전 수
//        int[] dp = new int[amount + 1];
//
//        // 만들 수 없는 큰 값으로 초기화 (amount+1은 절대 답이 될 수 없는 상한)
//        Arrays.fill(dp, amount + 1);
//        dp[0] = 0;
//
//        // 1원부터 amount원까지
//        for (int x = 1; x <= amount; x++) {
//            for (int coin : coins) {
//                if (x - coin >= 0) {
                      // x원을 만드는데 드는 동전 개수는 지금 만든 dp[x] 혹은 이전에 만든 것에 1을 더한 dp[x-coin]+1
//                    dp[x] = Math.min(dp[x], dp[x - coin] + 1);
//                }
//            }
//        }
//
//        return (dp[amount] == amount + 1) ? -1 : dp[amount];
    }

    public int bfs(int[] coins, int amount) {
        Deque<int[]> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        // {remain amount, number of coins}
        queue.offer(new int[]{amount, 0});

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int remain = current[0];
            int numCoins = current[1];

            if (remain == 0) return numCoins;

            for (int coin : coins) {
                int next = remain - coin;
                if (!visited.contains(next) && next >= 0) {
                    queue.offer(new int[]{next, numCoins + 1});
                    visited.add(next);
                }
            }
        }

        return -1;
    }
}

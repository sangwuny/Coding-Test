import java.util.*;

// https://school.programmers.co.kr/learn/courses/30/lessons/12982?language=java&gad_source=1&gad_campaignid=22499034228&gbraid=0AAAAAC_c4nCWBnGoYXe21aVKhl-DIyweQ&gclid=CjwKCAiA3-3KBhBiEiwA2x7FdF4Ajbufuxewb2Xn9oLUFaq9rHUbqUNKQ53V0a1ZHi7pgwk03nFB-RoCmjYQAvD_BwE

/**
 * Problem: Maximize supported departments within budget.
 * Input: costs, budget
 * Output: max count
 * Key: sort ascending, subtract greedily
 */

public class Budget {
    public int solution(int[] d, int budget) {
        Arrays.sort(d);

        int count = 0;
        for (int cost : d) {
            if (budget < cost) return count;
            budget -= cost;
            count++;
        }
        return count;
    }
}

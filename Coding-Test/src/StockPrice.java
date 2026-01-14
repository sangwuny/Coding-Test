import java.util.*;

public class StockPrice {
    public int[] solution(int[] prices) {
        int[] answer = new int[prices.length];
        Deque<Integer> stack = new ArrayDeque<>();

//        스택에는 값이 아닌 인덱스를 저장
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty()) {
                int j = stack.peek();

                if (prices[j] > prices[i]) {
                    answer[j] = i - j;
                    stack.pop();
                } else break;
            }

            stack.push(i);
        }

        while (!stack.isEmpty()) {
            int i = stack.pop();
            answer[i] = prices.length - i - 1;
        }

        return answer;
    }


//    기존 나의 풀이 방식
//    하지만 시간 복잡도 면에서 비효율 적임
//    public int[] solution(int[] prices) {
//        int[] answer = new int[prices.length];
//
//        for (int i = 0; i < prices.length; i++) {
//            for (int j = i + 1; j < prices.length; j++) {
//                if (prices[i] <= prices[j]) answer[i]++;
//                else {
//                    answer[i]++;
//                    break;
//                }
//            }
//        }
//
//        return answer;
//    }
}



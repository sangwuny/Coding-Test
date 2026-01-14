import java.util.*;

public class DailyTemperatures {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] answer = new int[temperatures.length];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < temperatures.length; i++) {
            while (!stack.isEmpty()) {
                int j = stack.peek();

//                날씨가 따뜻해지면 확인해봐야함
                if (temperatures[j] < temperatures[i]) {
                    answer[j] = i - j;
                    stack.pop();
                } else break;
            }

            stack.push(i);
        }

//        따뜻해 진 날이 없으면 0인데 초기값이 0으로 세팅되어 있으므로 추가 로직 필요하지 않음

        return answer;
    }
}

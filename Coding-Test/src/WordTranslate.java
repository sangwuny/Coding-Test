import java.util.*;
import java.util.stream.Stream;

/*
문제 파악: begin 단어가 주어졌으며 words 안에는 단어의 집합이 주어져 있다.
begin 단어에서 시작하여 한개의 알파벳만 바꿀 수 있으며 words 안에 있는 단어로만 변환이 가능하다.
이때 target 단어까지 변환시키는데 가장 짧은 변환 단계를 반환하라. 변환 불가능 하다면 0을 반환

제한 사항
1. 모든 단어의 길이는 동일
2. 소문자로만 구성
3. 중복 없음
4. begin!=target
 */

public class WordTranslate {
    public int solution(String begin, String target, String[] words) {
        // words 안에 target이 존재하지 않으면 변환 불가능
        int count = 0;
        for (String word : words) {
            if (target.equals(word)) count++;
        }
        if (count == 0) return 0;

        // begin도 words 안에 포함시킴
        String[] newWords = Stream.concat(Stream.of(begin), Arrays.stream(words)).toArray(String[]::new);

        return bfs(begin, target, newWords);
    }

    // 하나의 알파벳만 차이가 나는지 확인하는 함수
    public boolean canTranslate(String source, String target) {
        int diff = 0;

        for (int i = 0; i < source.length(); i++) {
            if (source.charAt(i) != target.charAt(i)) diff++;
            if (diff > 1) return false;
        }


        return diff == 1;
    }

    // target 까지의 변환
    public int bfs(String begin, String target, String[] words) {
        Deque<String> queue = new ArrayDeque<>();
        Set<String> visited = new HashSet<>();


        // words 내에서 변환 가능한 단어를 graph로 표현
        Map<String, List<String>> graph = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            graph.put(words[i], new ArrayList<>());
            for (int j = 0; j < words.length; j++) {
                if (i == j) continue;
                if (canTranslate(words[i], words[j])) graph.get(words[i]).add(words[j]);
            }
        }

        queue.offer(begin);
        visited.add(begin);

        int count = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                String cur = queue.poll();
                if (cur.equals(target)) return count;

                for (String next : graph.get(cur)) {
                    if (!visited.contains(next)) {
                        queue.offer(next);
                        visited.add(next);
                    }
                }
            }

            count++;
        }

        return 0;
    }
}

/* 과거 나의 풀이 - 이게 더 깔끔하네,,,
import java.util.*;

class Solution {
    public static class Node{
        String word;
        int depth;

        Node(String word, int depth){
            this.word = word;
            this.depth = depth;
        }
    }

    public boolean checkDiff(String a, String b){
        int count=0;
        for(int i=0;i<a.length();i++){
            if(!(a.charAt(i) == b.charAt(i))) count ++;
            if(count>1) return false;
        }

        return count==1;
    }

    public int solution(String begin, String target, String[] words) {
        // target이 words에 없다면 변환 불가능
        int count=0;
        for(String word : words){
            if(word.equals(target)) count++;
        }
        if(count==0) return 0;

        boolean[] visited = new boolean[words.length];
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(new Node(begin,0));

        while(!queue.isEmpty()){
            Node current = queue.remove();

            if(current.word.equals(target)) return current.depth;

            for(int i=0;i<words.length;i++){
                if(checkDiff(current.word, words[i]) && !visited[i]){
                    queue.add(new Node(words[i], current.depth+1));
                    visited[i]=true;
                }
            }
        }

        return 0;
    }
}
 */
import java.util.*;

public class IncompleteRunner {
    public String solution(String[] participant, String[] completion) {
        Map<String, Integer> map = new HashMap<>();
        for (String key : participant) {
            map.put(key, map.getOrDefault(key, 0) + 1);
        }

        for(String key: completion){
            if(map.containsKey(key)){
                map.put(key, map.get(key) - 1);
            }
        }

        for(Map.Entry<String,Integer> e : map.entrySet()){
            if(e.getValue()!=0) return e.getKey();
        }

        return null;
    }
}

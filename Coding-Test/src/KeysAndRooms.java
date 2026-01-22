import java.util.*;

public class KeysAndRooms {
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        return bfs(rooms);
    }

    public boolean bfs(List<List<Integer>> rooms){
        Deque<Integer> queue = new ArrayDeque<>();
        Set<Integer> visited = new HashSet<>();

        queue.offer(0);
        visited.add(0);

        while(!queue.isEmpty()){
            int curVertex = queue.poll();

            for(int nextVertex : rooms.get(curVertex)){
                if(!visited.contains(nextVertex)){
                    queue.offer(nextVertex);
                    visited.add(nextVertex);
                }
            }
        }

        for(int room=0;room<rooms.size();room++){
            if(!visited.contains(room)) return false;
        }

        return true;
    }
}

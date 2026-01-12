import java.util.*;

public class PhoneBook {
    public boolean solution(String[] phone_book) {
        String[] sorted = Arrays.stream(phone_book).sorted().toArray(String[]::new);

        for(int i=0;i< sorted.length-1;i++){
            if(sorted[i+1].startsWith(sorted[i])) return false;
        }

        return true;
    }
}

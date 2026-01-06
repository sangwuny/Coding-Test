import java.util.*;

// https://school.programmers.co.kr/learn/courses/30/lessons/12909?language=swift&gad_source=1&gbraid=0AAAAAC_c4nDwcnLeeBzdE5er_6zlYj7tf&gclid=Cj0KCQjwqv2_BhC0ARIsAFb5Ac-Ng7uTEMK7P7iUU5ihQ7iwvUDIhWTZwhxRT-kmlfOTtX3d5D7K-FQaAuemEALw_wcB

/**
 * Check if a parentheses string is valid.
 * Input: String s
 * Output: boolean
 * Key: counter-based scan
 */

public class ValidParenthesis {
    class Solution {
        boolean solution(String s) {
            Deque<Character> stack = new ArrayDeque<>();

            stack.addLast(s.charAt(0));

            for(int i=1;i<s.length();i++){
                if(!stack.isEmpty() && stack.peekLast()=='(' && s.charAt(i)==')'){
                    stack.removeLast();
                }
                else{
                    stack.addLast(s.charAt(i));
                }
            }

            return stack.isEmpty();
        }
    }
}

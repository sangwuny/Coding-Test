import java.util.*;

// https://leetcode.com/problems/two-sum/description/

/**
 * Find two indices whose sum equals target.
 * Input: nums, target
 * Output: indices
 * Key: one-pass HashMap
 */

public class TwoSum {
//        기존 나의 풀이 방식
//    public int[] twoSum(int[] nums, int target) {
//        for (int i = 0; i < nums.length; i++) {
//            int temp = target - nums[i];
//            for (int j = i + 1; j < nums.length; j++) {
//                if (nums[j] == temp) return new int[]{i, j};
//            }
//        }
//
//        return null;
//    }

    //    Hash Table을 이용한 방식
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int need = target - nums[i];

            if(map.containsKey(need)) return new int[]{map.get(need), i};

            map.put(nums[i], i);
        }

        return new int[]{};
    }
}

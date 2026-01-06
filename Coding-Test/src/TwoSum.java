import java.util.*;

// https://leetcode.com/problems/two-sum/description/

public class TwoSum {
    class Solution {
        public int[] twoSum(int[] nums, int target) {
            for(int i=0;i<nums.length;i++){
                int temp = target - nums[i];
                for(int j=i+1;j<nums.length;j++){
                    if(nums[j]==temp) return new int[]{i,j};
                }
            }

            return null;
        }
    }
}

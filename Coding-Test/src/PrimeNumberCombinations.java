import java.util.*;

/**
 * Find the number of combinations of three numbers whose sum is prime.
 * Input: int[] nums
 * Output: int count (number of prime-sums)
 * Key:
 *   1) Triple nested loop for all unique 3-element combinations
 *   2) Check if sum is a prime number
 *   3) Count only when sum is prime
 */

public class PrimeNumberCombinations {
    public int solution(int[] nums) {
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    int sum = nums[i] + nums[j] + nums[k];
                    if(validPrime(sum)) count++;
                }
            }
        }

        return count;
    }

    public boolean validPrime(int num) {
        for (int i = 2; i < num; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }
}

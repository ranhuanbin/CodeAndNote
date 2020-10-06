package com.hb.test.algorithm.lt.lt11;

/**
 * 给定一个整数数组nums，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 */
public class Lt11 {
    public static void testForLt11() {
        maxSubArray(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
    }

    public static int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = 0;
        for (int num : nums) {
            if (sum < 0) {
                sum = num;
            } else {
                sum += num;
            }
            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }
}

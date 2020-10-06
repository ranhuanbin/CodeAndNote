package com.hb.test.algorithm.lt.lt08;

/**
 * 给你一个数组nums和一个值val，你需要原地移除所有数值等于val的元素，并返回移除后数组的新长度。
 * 不要使用额外的数组空间，你必须仅使用O(1)额外空间并原地修改输入数组。
 * 元素的顺序可以改变。你不需要考虑数组中超出新长度后面的元素。
 */
public class Lt08 {
    public static void testForLt08() {

    }

    public static int removeElement(int[] nums, int val) {
        int i = 0, j = 0;
        for (; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }
}

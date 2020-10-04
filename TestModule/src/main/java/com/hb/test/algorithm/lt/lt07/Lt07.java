package com.hb.test.algorithm.lt.lt07;

import com.hb.test.utils.LogUtils;

/**
 * 给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
 * <p>
 * 不要使用额外的数组空间，你必须在原地修改输入数组并在使用O(1)额外空间的条件下完成。
 */
public class Lt07 {
    public static final String TAG = "Lt07";

    public static void testForLt07() {
//        int i = removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4});
        int i = removeDuplicates(new int[]{1, 1, 2});
        LogUtils.v(TAG, Lt07.class, "i: " + i);
    }

    // [1, 1, 2]
    // [0, 0, 1, 1, 1, 2, 2, 3, 3, 4]
    public static int removeDuplicates(int[] nums) {
        int i = 0;
        for (int j = 1; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                i++;
                nums[i] = nums[j];
            }
        }
        return i + 1;
    }
}

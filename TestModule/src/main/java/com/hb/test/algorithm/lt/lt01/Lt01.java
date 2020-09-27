package com.hb.test.algorithm.lt.lt01;

import com.hb.test.algorithm.IFO;

public class Lt01 extends IFO {
    public static int[] twoSum(int[] arrs, int target) {
        int firstIndex = 0;
        int lastIndex = arrs.length - 1;
        int sum;
        for (int i = 0; i < arrs.length; i++) {
            if (firstIndex >= lastIndex) {
                return null;
            }
            sum = arrs[firstIndex] + arrs[lastIndex];
            if (sum > target) {
                lastIndex--;
            } else if (sum < target) {
                firstIndex++;
            } else {
                return new int[]{firstIndex, lastIndex};
            }
        }
        return new int[]{firstIndex, lastIndex};
    }
}
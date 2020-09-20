package com.hb.test.algorithm.foroffer27;

import com.hb.test.algorithm.IForOffer;

/**
 * 输入n个整数，找出其中最小的k个数。
 */
public class ForOffer27 extends IForOffer {
    public static void testForOffer27(int[] inputArr, int k) {
        checkInput(inputArr, k);
        int length = inputArr.length;
        int[] arr = new int[k];
        System.arraycopy(inputArr, 0, arr, 0, k);
        int[] max = getMax(arr);
        for (int i = k; i < length; i++) {
            int num = inputArr[i];
            if (num < max[0]) {
                arr[max[1]] = num;
                max = getMax(arr);
            }
        }
    }

    private static int[] getMax(int[] arr) {
        int length = arr.length;
        int[] num = new int[2];
        for (int i = 0; i < length; i++) {
            if (num[0] < arr[i]) {
                num[0] = arr[i];
                num[1] = i;
            }
        }
        return num;
    }
}

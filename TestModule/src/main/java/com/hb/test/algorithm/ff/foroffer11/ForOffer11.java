package com.hb.test.algorithm.ff.foroffer11;

/**
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有奇数位于数组的前半部分，所有偶数位予数组的后半部分。
 */
public class ForOffer11 {
    public static void forOffer11_1(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("input invalid");
        }
        int leftIndex = 0, rightIndex = arr.length - 1;
        while (leftIndex < rightIndex) {
            // 1.左:偶, 右:奇
            if (arr[leftIndex] % 2 == 0 && arr[rightIndex] % 2 != 0) {
                int tmp = arr[leftIndex];
                arr[leftIndex] = arr[rightIndex];
                arr[rightIndex] = tmp;
                continue;
            }
            // 2.左:偶, 右:偶
            if (arr[leftIndex] % 2 == 0 && arr[rightIndex] % 2 == 0) {
                rightIndex--;
                continue;
            }
            // 3.左:奇, 右:奇
            if (arr[leftIndex] % 2 != 0 && arr[rightIndex] % 2 != 0) {
                leftIndex++;
                continue;
            }
            // 4.左:奇, 右:偶
            if (arr[leftIndex] % 2 != 0 && arr[rightIndex] % 2 == 0) {
                leftIndex++;
                rightIndex--;
            }
        }
    }

    public static void forOffer11_2(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("input invalid");
        }
        int leftIndex = 0, rightIndex = arr.length - 1;
        while (leftIndex < rightIndex) {
            // 1.左侧开始找偶数
            while (leftIndex < rightIndex && arr[leftIndex] % 2 != 0) {
                leftIndex++;
            }
            // 2.右侧开始找奇数
            while (leftIndex < rightIndex && arr[rightIndex] % 2 == 0) {
                rightIndex--;
            }
            int tmp = arr[leftIndex];
            arr[leftIndex] = arr[rightIndex];
            arr[rightIndex] = tmp;
        }
    }
}

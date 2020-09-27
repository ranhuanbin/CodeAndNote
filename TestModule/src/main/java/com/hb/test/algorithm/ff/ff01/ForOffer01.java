package com.hb.test.algorithm.ff.ff01;

import com.hb.test.utils.LogUtils;

/**
 * https://github.com/LRH1993/android_interview/blob/master/algorithm/For-offer/01.md
 * 在一个二维数组中，每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。
 * 请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class ForOffer01 {
    public static final String TAG = "ForOffer01";
    private static int[][] arrays = new int[][]{
            {1, 2, 3, 4, 5},
            {2, 3, 4, 5, 6},
            {3, 4, 5, 6, 7},
            {4, 5, 6, 7, 8}};

    public static void test(int target) {
        if (arrays == null || arrays.length == 0) {
            return;
        }
        int columns = arrays.length;
        int rows = arrays[0].length;
        int column = columns - 1;
        int row = 0;
        while (row >= 0 && row < rows && column >= 0 && column < columns) {
            if (arrays[column][row] == target) {
                LogUtils.v(TAG, ForOffer01.class, "row: " + row + ", column:" + column);
                row++;
            } else if (arrays[column][row] > target) {
                column--;
            } else {
                row++;
            }
        }
        LogUtils.v(TAG, ForOffer01.class, "finish");
    }
}

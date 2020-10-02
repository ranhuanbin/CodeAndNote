package com.hb.test.algorithm.lt.lt03;

import com.hb.test.utils.LogUtils;

/**
 * 给出一个32位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 * input: 123, output: 123
 * input: 120, output: 21
 */
public class Lt03 {
    public static final String TAG = "Lt03";

    public static void testLt03(int x) {
        int result = 0;
        while (x != 0) {
            if (result > (2 ^ 31 - 1) || result < (-2 ^ 31)) {
                return;
            }
            result = result * 10 + x % 10;
            x = x / 10;
        }
        LogUtils.v(TAG, Lt03.class, "result: " + result);
    }
}

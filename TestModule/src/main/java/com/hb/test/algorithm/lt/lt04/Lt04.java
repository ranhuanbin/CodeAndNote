package com.hb.test.algorithm.lt.lt04;

import com.hb.test.utils.LogUtils;

/**
 * 判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
 */
public class Lt04 {
    public static final String TAG = "LT04";

    public static void testLt04(int x) {
        if (x < 0) {
            return;
        }
        if (x > 0 && x % 10 == 0) {
            return;
        }
        //逆序
        int result = 0;
        while (x > result) {
            result = result * 10 + x % 10;
            x /= 10;
        }
        LogUtils.v(TAG, Lt04.class, "equals: " + ((result == x) || x == result / 10));
    }
}

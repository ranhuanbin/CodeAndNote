package com.hb.test.algorithm.lt.lt05;

import android.text.TextUtils;

import com.hb.test.utils.LogUtils;

/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 * <p>
 * 如果不存在公共前缀，返回空字符串 ""。
 */
public class Lt05 {
    public static final String TAG = "Lt05";

    public static void testLt05(String[] strs) {
        if (strs == null || strs.length == 0) {
            return;
        }
        String val = strs[0];
        for (int i = 1; i < strs.length; i++) {
            int j = 0;
            for (; j < strs[i].length() && j < val.length(); j++) {
                if (val.charAt(j) != strs[i].charAt(j)) {
                    break;
                }
            }
            val = val.substring(0, j);
            if (TextUtils.isEmpty(val)) {
                return;
            }
        }
        LogUtils.v(TAG, Lt05.class, "val: " + val);
    }
}

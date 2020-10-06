package com.hb.test.algorithm.lt.lt09;

import android.text.TextUtils;

/**
 * 给定一个haystack字符串和一个needle字符串，在haystack字符串中找出needle字符串出现的第一个位置 (从0开始)。如果不存在，则返回-1。
 * 如何使用kmp算法解这个题?
 */
public class Lt09 {
    public static void testForLt09() {

    }

    public static int strStr(String haystack, String needle) {
        if (TextUtils.isEmpty(haystack)) {
            return -1;
        }
        if (TextUtils.isEmpty(needle)) {
            return 0;
        }
        int length = needle.length();
        int haystackLength = haystack.length();
        for (int i = 0; i < haystackLength; i++) {
            String substring = haystack.substring(i, i + length);
            if (needle.equals(substring)) {
                return i;
            }
        }
        return -1;
    }
}

package com.hb.test.algorithm.foroffer02;

import com.hb.test.utils.LogUtils;

/**
 * 请实现一个函数，把字符串中的每个空格替换成"%20"，例如“We are happy.”，则输出“We%20are%20happy.”。
 */
public class ForOffer02 {
    public static final String TAG = "ForOffer02";
    private static String text = "We are happy.";

    public static void replaceBlank() {
        int count = 0;
        String[] split = text.split("");
        for (String s : split) {
            if (" ".equals(s)) {
                count++;
            }
        }
        String[] newText = new String[text.length() + 2 * count];
        int index = 0;
        for (int i = 0; i < text.length(); i++) {
            if (" ".equals(split[i])) {
                newText[index++] = "%";
                newText[index++] = "2";
                newText[index++] = "0";
            } else {
                newText[index++] = split[i];
            }
        }
        LogUtils.v(TAG, ForOffer02.class, "newText: " + newText.toString());
    }
}

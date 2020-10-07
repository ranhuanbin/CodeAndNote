package com.hb.test.algorithm.lt.lt12;

/**
 * 给定一个仅包含大小写字母和空格' '的字符串s，返回其最后一个单词的长度。如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
 * 如果不存在最后一个单词，请返回0。
 * 说明：一个单词是指仅由字母组成、不包含任何空格字符的最大子字符串。
 */
public class Lt12 {
    public static void testForLt12() {

    }

    public static int lengthOfLastWord(String s) {
        int endIndex = -1;
        int index = s.length() - 1;
        while (index >= 0) {
            if (' ' != s.charAt(index)) {
                if (endIndex == -1) {
                    endIndex = index;
                }
                index--;
            } else {
                if (endIndex == -1) {
                    index--;
                } else {
                    break;
                }
            }
        }
        return endIndex - index;
    }
}

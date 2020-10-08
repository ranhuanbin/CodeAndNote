package com.hb.test.algorithm.lt.lt14;

/**
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 * <p>
 * 输入为 非空 字符串且只包含数字 1 和 0。
 */
public class Lt14 {
    public static void testForLt14() {
        addBinary("1010", "1011");
        addBinary1("1010", "1011");
    }

    public static String addBinary(String a, String b) {
        int dis = Math.abs(a.length() - b.length());
        if (a.length() > b.length()) {
            StringBuilder bBuilder = new StringBuilder(b);
            for (int i = 0; i < dis; i++) {
                bBuilder.insert(0, "0");
            }
            b = bBuilder.toString();
        } else if (a.length() < b.length()) {
            StringBuilder aBuilder = new StringBuilder(a);
            for (int i = 0; i < dis; i++) {
                aBuilder.insert(0, "0");
            }
            a = aBuilder.toString();
        }
        int[] chars = new int[a.length()];
        for (int i = 0; i < a.length(); i++) {
            chars[i] = (a.charAt(i) + b.charAt(i)) - 2 * '0';
        }
        StringBuilder result = new StringBuilder();
        int flag = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            chars[i] += flag;
            flag = chars[i] >> 1;
            result.insert(0, flag == 1 ? chars[i] % 2 : chars[i]);
        }
        if (flag == 0) {
            return result.toString();
        } else {
            return "1" + result;
        }
    }

    public static String addBinary1(String a, String b) {
        StringBuilder builder = new StringBuilder();
        int flag = 0;
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int sum = flag;
            sum += i >= 0 ? a.charAt(i) - '0' : 0;
            sum += j >= 0 ? b.charAt(j) - '0' : 0;
            builder.append(sum % 2);
            flag = sum / 2;
        }
        builder.append(flag == 1 ? flag : "");
        return builder.reverse().toString();
    }
}

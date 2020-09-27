package com.hb.test.algorithm.ff.foroffer26;

/**
 * 数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 */
public class ForOffer26 {
    public static void testForOffer25(int[] input) {
        if (input == null || input.length == 0) {
            throw new IllegalArgumentException("input is invalid");
        }
        int count = 1;
        int result = input[0];
        for (int i = 1; i < input.length; i++) {
            if (result == input[i]) {
                count++;
            } else {
                count--;
            }
            if (count == 0) {
                result = input[i];
                count = 1;
            }
        }
    }
}

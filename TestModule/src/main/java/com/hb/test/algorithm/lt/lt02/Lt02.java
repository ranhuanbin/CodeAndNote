package com.hb.test.algorithm.lt.lt02;

import java.util.HashSet;

/**
 * 给定字符串J代表石头中宝石的类型，和字符串S代表你拥有的石头。S中每个字符代表了一种你拥有的石头的类型，
 * 你想知道你拥有的石头中有多少是宝石。
 * <p>
 * J中的字母不重复，J和S中的所有字符都是字母。字母区分大小写，因此"a"和"A"是不同类型的石头。
 */
public class Lt02 {
    public static int test(String J, String S) {
        int sum = 0;
        HashSet<Character> charMaps = new HashSet<>(J.length());
        for (Character j : J.toCharArray()) {
            charMaps.add(j);
        }
        for (Character s : S.toCharArray()) {
            if (charMaps.contains(s)) {
                sum++;
            }
        }
        return sum;
    }
}

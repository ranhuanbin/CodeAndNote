package test;

import java.util.Arrays;

public class KMPTest {
    public static int KMP(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0, j = 0;
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == p.length) {
            return i - j;
        }
        return -1;
    }

    /**
     * ABCDEEEEEABCEABCDEEEEEABCF
     * ABCDEEEEEABCD
     *   A  B  C  D  E  E  E  E  E  A  B  C  D
     * [-1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3]
     */
    public static int[] getNext(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        int j = 0, k = -1;
        next[0] = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        System.out.println("next = " + Arrays.toString(next));
        return next;
    }
}

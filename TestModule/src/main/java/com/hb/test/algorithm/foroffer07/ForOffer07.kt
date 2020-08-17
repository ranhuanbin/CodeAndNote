package com.hb.test.algorithm.foroffer07


/**
 * 写一个函数，输入n，求斐波那契数列的第n项值。
 * 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233
 * 1. 循环方式
 * 2. 递归方式
 */
class ForOffer07 {
    companion object {
        fun xunhuan(n: Int): Int {
            if (n == 0) {
                return 0
            }
            if (n == 1 || n == 2) {
                return 1;
            }
            var cur = 0
            var pre = 1
            var ppre = 1
            for (index in 3..n) {
                cur = pre + ppre
                ppre = pre
                pre = cur
            }
            return cur
        }

        fun digui(n: Int): Int {
            if (n == 0) {
                return 0
            }
            if (n == 1 || n == 2) {
                return 1
            }
            return digui(n - 1) + digui(n - 2)
        }
    }
}
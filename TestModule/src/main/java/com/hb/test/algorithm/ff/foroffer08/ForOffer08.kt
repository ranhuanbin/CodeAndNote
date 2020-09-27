package com.hb.test.algorithm.ff.foroffer08

import android.util.Log

/**
 * 请实现一个函数，输入一个整数，输出该数二进制表示中1的个数。例如把9表示成二进制1001，有2位1。因此如果输入9，该函数输出2。
 * 9 = 1001
 * 7 = 0111
 */
class ForOffer08 {
    companion object {
        val TAG = "ForOffer08"
        fun forOffer08(n: Int) {
            var temp: Int = n
            var count = 0
            while (temp > 0) {
                count += temp % 2
                temp = temp.ushr(1)
                Log.v(TAG, "count: " + count + ", temp: " + temp)
            }
            Log.v(TAG, "count: " + count)
        }
    }
}



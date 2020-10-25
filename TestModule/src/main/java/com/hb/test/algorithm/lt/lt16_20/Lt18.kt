package com.hb.test.algorithm.lt.lt16_20

import android.util.Log

/**
 * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
 */
object Lt18 {
    fun testForLt18() {
        val singleNumber = singleNumber(intArrayOf(2, 2, 1))
        val singleNumber1 = singleNumber(intArrayOf(4, 1, 2, 1, 2))
        Log.v("AndroidTest", "singleNumber: $singleNumber, singleNumber1: $singleNumber1")
    }

    fun singleNumber(nums: IntArray): Int {
        var num = nums[0]
        for (index in 1 until nums.size) {
            num = num.xor(nums[index])
        }
        return num
    }
}
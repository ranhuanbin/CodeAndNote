package com.hb.test.algorithm.lt.array

class ArrayLt03 {
    fun shuffle(nums: IntArray, n: Int): IntArray {
        val numsTmp = IntArray(nums.size)
        for (index in 0 until n) {
            numsTmp[2 * index] = nums[index]
            numsTmp[2 * index + 1] = nums[index + n]
        }
        return numsTmp
    }
}
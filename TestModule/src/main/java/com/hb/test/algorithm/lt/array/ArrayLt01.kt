package com.hb.test.algorithm.lt.array

object ArrayLt01 {
    fun runningSum() {

    }

    fun runningSum(nums: IntArray): IntArray {
        for (index in 1 until nums.size) {
            nums[index] += nums[index - 1]
        }
        return nums
    }
}
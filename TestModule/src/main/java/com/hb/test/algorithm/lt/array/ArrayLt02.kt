package com.hb.test.algorithm.lt.array

class ArrayLt02 {
    fun numIndenticalPairs(nums: IntArray): Int {
        val numsTmp = IntArray(100)
        var sum = 0
        nums.forEach {
            /**
             * 这里用到了动态规划的概念.
             * f(n) = data(n-1) + f(n-1)
             * sum = sum + numTmp[it]
             * 这段与爬楼梯的代码非常的相似
             */
            sum += numsTmp[it]
            numsTmp[it]++
        }
        return sum
    }
}
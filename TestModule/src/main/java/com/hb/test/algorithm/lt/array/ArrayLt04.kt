package com.hb.test.algorithm.lt.array

class ArrayLt04 {
    fun kidsWithCandies(candies: IntArray, extraCandies: Int): BooleanArray {
        var max = candies[0]
        val result = BooleanArray(candies.size)
        candies.forEach {
            if (max < it) {
                max = it
            }
        }
        for (index in candies.indices) {
            result[index] = candies[index] + extraCandies >= max
        }
        return result
    }
}
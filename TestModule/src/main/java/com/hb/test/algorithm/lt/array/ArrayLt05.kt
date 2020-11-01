package com.hb.test.algorithm.lt.array

class ArrayLt05 {
    fun sumOddLengthSubarrays(arr: IntArray): Int {
        var sum = 0
        arr.indices.forEach { index ->
            val left = index + 1
            val right = arr.size - index

            val left_odd = left / 2
            val right_odd = right / 2
            // 妙啊, 这个+1解决了总数是奇数的情况
            val left_even = (left + 1) / 2
            val right_event = (right + 1) / 2

            sum += arr[index] * (left_odd * right_odd + left_even * right_event)
        }
        return sum
    }
}
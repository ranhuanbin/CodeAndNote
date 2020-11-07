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

    /**
     * 搜索插入位置
     * 给定一个排序数组和一个目标值，在数组中找到目标值，并返回其索引。如果目标值不存在于数组中，返回它将会被按顺序插入的位置。
     * 你可以假设数组中无重复元素。
     */
    fun testForArrayLt06(nums: IntArray, target: Int): Int {
        var max = nums.size - 1
        var min = 0
        var mid: Int
        while (min <= max) {
            mid = (max + min) / 2
            if (nums[mid] == target) {
                return mid
            }
            if (nums[mid] > target) {
                max = mid
            } else {
                // min = mid:  这种写法结果就是while无限循环, 无法跳出来了, 既然nums[mid] < target, 那为何left不从mid+1位开始呢
                min = mid + 1
            }
        }

        return 0
    }

    /**
     * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
     */
    fun testForArrayLt07(nums: IntArray): Int {
        var max = nums[0]
        var sum = max
        for (index in 1 until nums.size) {
            if (sum < 0) {
                sum = nums[index]
            } else {
                sum += nums[index]
            }
            if (max < sum) {
                max = sum
            }
        }
        return max
    }
}


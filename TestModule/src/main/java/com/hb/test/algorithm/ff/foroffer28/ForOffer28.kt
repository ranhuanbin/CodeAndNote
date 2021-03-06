package com.hb.test.algorithm.ff.foroffer28

/**
 * 输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为O(n)。
 * 例子说明：
 * 例如输入的数组为{1, -2, 3, 10, -4, 7, 2, -5}，和最大的子数组为｛3, 10, -4, 7, 2}。因此输出为该子数组的和18。
 */
object ForOffer28 {
    fun findSubArray(arr: Array<Int>): Int {
        var max = 0
        var curMax = 0
        if (arr.size == 0) {
            return -1
        }
        arr.forEach {
            if (curMax <= 0) {
                curMax = it
            } else {
                curMax += it
            }
            if (max < curMax) {
                max = curMax
            }
        }
        return max
    }
}
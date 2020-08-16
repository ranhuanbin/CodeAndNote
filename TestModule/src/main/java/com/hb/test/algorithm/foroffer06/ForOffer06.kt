package com.hb.test.algorithm.foroffer06

import java.lang.IllegalArgumentException

class ForOffer06 {
    companion object {
        fun forOffer06(numbsers: Array<Int>): Int {
            if (numbsers.size == 0) {
                throw IllegalArgumentException("invalid input")
            }
            var lo = 0
            var hi = numbsers.size - 1
            var min = lo
            while (numbsers[lo] >= numbsers[hi]) {
                if (hi - lo == 1) {
                    return numbsers[hi];
                }
                min = lo + (hi - lo) / 2
                if (numbsers[min] == numbsers[hi] && numbsers[min] == numbsers[lo]) {
                    return minInorder(numbsers, lo, hi)
                }
                if (numbsers(min) >= numbsers[lo]) {
                    hi = min
                } else if (numbsers(min) <= numbsers[hi]) {
                    lo = min
                }
            }
            return numbsers[min]
        }

        fun minInorder(numbsers: Array<Int>, start: Int, end: Int): Int {
            var min = numbsers[start]
            for (index in start..end) {
                if (numbsers[index] < min) {
                    min = numbsers[index]
                }
            }
            return min
        }
    }
}


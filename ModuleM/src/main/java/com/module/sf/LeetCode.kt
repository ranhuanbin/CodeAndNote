package com.module.sf

import android.os.Build
import androidx.annotation.RequiresApi
import java.util.*
import kotlin.collections.HashMap


class LeetCode {
    companion object {
        fun findLucky(): Int {
            val aar = listOf(1, 2, 2, 3, 3, 3)

            val tmpArr = IntArray(501)

            aar.forEach { i ->
                tmpArr[i]++
            }

            tmpArr.forEach { i ->
                if (i == tmpArr[i] && tmpArr[i] != 0) {
                    return tmpArr[i]
                }
            }
            return -1
        }

        @RequiresApi(Build.VERSION_CODES.N)
        fun firstUniqChar(s: String): Int {
            val map = HashMap<Char, Int?>()
            for (i in s.indices) {
                if (map.containsKey(s[i])) {
                    map[s[i]] = map.getOrDefault(s[i], 0)
                } else {
                    map[s[i]] = 1
                }
            }
            val entries: Set<Map.Entry<Char, Int?>> = map.entries
            for ((key, value) in entries) {
                if (value == 1) {
                    return key.toInt()
                }
            }
            return -1
        }

        /*** 有效的括号 */
        @RequiresApi(Build.VERSION_CODES.N)
        fun isValid(s: String): Boolean {
            if (s.length % 2 == 1) {
                return false
            }
            val stack = LinkedList<Char>()
            val map = mapOf('}' to '{', ')' to '(', ']' to '[')
            s.toCharArray().forEach {
                if (map.containsKey(it)) {
                    if (stack.isEmpty() || stack.pop() != map[it]) {
                        return false
                    }
                } else {
                    stack.push(it)
                }
            }
            return stack.isEmpty()
        }

        fun isPalindrome(x: Int): Boolean {
            if (x < 0) {
                return false
            }
            if (x < 10) {
                return true
            }
            var tmp: Int = x
            var result = 0
            while (tmp > 0) {
                result = result * 10 + tmp % 10
                tmp /= 10
            }
            return result == x
        }

        fun moveZeroes(nums: IntArray): Unit {
            val length = nums.size
            var left = 0
            var right = 0
            while (right < length) {
                if (nums[right] != 0) {
                    val tmp = nums[left]
                    nums[left] = nums[right]
                    nums[right] = tmp
                    left++
                }
                right++
            }
        }

        fun merge(A: IntArray, m: Int, B: IntArray, n: Int): Unit {
            var p = m + n - 1
            var pa = m - 1
            var pb = n - 1
            while (pa >= 0 && pa >= 0) {
                A[p--] = if (A[pa] > B[pb]) A[pa--] else B[pb--]
            }
            System.arraycopy(B, 0, A, 0, pb + 1)
        }

        fun mergeTwoLists(l1: ListNode?, l2: ListNode?): ListNode? {
            if (l1 == null) {
                return l2
            }
            if (l2 == null) {
                return l1
            }
            var tl1 = l1
            var tl2 = l2
            var headNode = ListNode(0)
            var preNode: ListNode = headNode
            while (tl1 != null && tl2 != null) {
                if (tl1.value > tl2.value) {
                    headNode.next = tl1
                    tl1 = tl1.next
                } else {
                    headNode.next = tl2
                    tl2 = tl2.next
                }
                headNode = headNode.next!!
            }
            return preNode.next
        }

//        class ListNode(var `val`: Int, var next: ListNode?)

        fun backspaceCompare(S: String, T: String): Boolean {
            val stack1 = Stack<Char>()
            val stack2 = Stack<Char>()
            var i = 0
            var j = 0
            while (i < S.length || j < T.length) {
                if (i < S.length) {
                    if (S[i] != '#') {
                        stack1.push(S[i])
                    } else if (S[i] == '#' && stack1.isNotEmpty()) {
                        stack1.pop()
                    }
                    i++
                }
                if (j < T.length) {
                    if (T[j] != '#') {
                        stack2.push(T[j])
                    } else if (T[j] == '#' && stack2.isNotEmpty()) {
                        stack2.pop()
                    }
                    j++
                }
            }
            if (stack1.size != stack2.size) {
                return false
            }
            while (stack1.isNotEmpty()) {
                if (stack1.pop() != stack2.pop()) {
                    return false
                }
            }
            return true
        }

        fun hammingDistance(x: Int, y: Int): Int {
            var count = 0
            var i = x
            var j = y
            while (i != 0 || j != 0) {
                if (i % 2 != j % 2) {
                    count++
                }
                i /= 2
                j /= 2
            }
            return count
        }

        fun isIsomorphic(s: String, t: String): Boolean {
            if (s.length != t.length) {
                return false
            }
            val map = mutableMapOf<Char, Char>()
            for (i in s.indices) {
                if (map[s[i]] == null) {
                    map[s[i]] = t[i]
                } else if (map[s[i]] != t[i]) {
                    return false
                }
            }
            return true
        }

        fun findMaxAverage(nums: IntArray, k: Int): Double {
            if (nums.size < k) {
                return 0.0
            }
            var sum = 0.0
            for (i in 0 until k) {
                sum += nums[i]
            }
            var max = sum
            for (i in k until nums.size) {
                sum = sum + nums[i] - nums[i - k]
                if (sum > max) {
                    max = sum
                }
            }
            return max / (k * 1.0)
        }

        fun findRepeatNumber(nums: IntArray): Int {
            val res = IntArray(nums.size)
            nums.forEach {
                res[it]++
                if (res[it] != 1) {
                    return it
                }
            }
            return 0
        }

    }

    class ListNode(var value: Int) {
        var next: ListNode? = null
    }
}
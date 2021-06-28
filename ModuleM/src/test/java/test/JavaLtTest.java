package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * 1. 买卖股票 {@link #maxProfit(int[])}
 * 2. 买卖股票的最佳时机 {@link #maxProfit1(int[])}
 * 3. 只出现一次的数字 {@link #singleNumber(int[])}
 * 4. 投票算法: 多数元素 {@link #majorityElement(int[])}
 * 5. 贪心: 爬楼梯 {@link #climbStairs(int)}
 * 6. 堆排序: 最小的K个数 {@link #getLeastNumbers(int[], int)}
 * 7. 二分查找: 旋转数组的最小数字 {@link #minArray(int[])}
 * 8. 边界条件:
 * <p>       (1) 调整数组顺序使奇数位于偶数前面
 * 9. 种花问题
 * <p>
 * <p>
 * 5. 未做出来
 * (1) x的平方根 {@link #mySqrt(int)}
 */
public class JavaLtTest extends TestCase {
    @Test
    public void test() {
//        reverse(1463847412);
//        lengthOfLastWord("Hello world");
//        canPlaceFlowers(new int[]{1, 0, 0, 0, 1}, 2);
    }

    //    public int[] twoSum(int[] numbers, int target) {
//
//    }

    /**
     * 种花问题
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     * https://leetcode-cn.com/problems/can-place-flowers/
     */
    public boolean canPlaceFlowers(int[] flowerbed, int n) {
        int count = 0;
        for (int i = 0; i < flowerbed.length; i++) {
            if (flowerbed[i] == 0) {
                if (i == 0 && i == flowerbed.length - 1) {// 数组只有一个元素
                    flowerbed[i] = 1;
                    count++;
                } else if (i == 0) {// 第一个元素
                    if (flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        count++;
                    }
                } else if (i == flowerbed.length - 1) {// 最后一个元素
                    if (flowerbed[i - 1] == 0) {
                        flowerbed[i] = 1;
                        count++;
                    }
                } else {// 中间元素
                    if (flowerbed[i - 1] == 0 && flowerbed[i + 1] == 0) {
                        flowerbed[i] = 1;
                        count++;
                    }
                }
            }
        }
        return count >= n;
    }


    /**
     * 最长回文串
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int longestPalindrome(String s) {
        int count = 0;
        char[] chs = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (char ch : chs) {
            if (set.contains(ch)) {
                count++;
                set.remove(ch);
            } else {
                set.add(ch);
            }
        }
        return set.isEmpty() ? count << 1 : (count << 1) + 1;
    }

    /**
     * 存在重复元素
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }
        return false;
    }

    /**
     * 两数之和
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int[] twoSum(int[] numbers, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(target - numbers[i])) {
                return new int[]{map.get(target - numbers[i]) + 1, i + 1};
            }
            map.put(numbers[i], i);
        }
        return new int[2];
    }

    /**
     * 调整数组顺序使奇数位于偶数前面
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int[] exchange(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            if (nums[i] % 2 == 1 && nums[j] % 2 == 1) {
                int temp = nums[i];
                nums[i] = nums[j];
                nums[j] = temp;
            } else if (nums[i] % 2 == 0) {
                i++;
            } else if (nums[j] % 2 == 1) {
                j--;
            }
        }
        return nums;
    }

    /**
     * 旋转数组的最小数字
     * 时间复杂度 o(logN)
     * 空间复杂度 o(1)
     * https://leetcode-cn.com/problems/xuan-zhuan-shu-zu-de-zui-xiao-shu-zi-lcof/
     */
    public int minArray(int[] numbers) {
        int l = 0;
        int h = numbers.length - 1;
        int m = l + (h - l) / 2;
        while (l < h) {
            if (numbers[m] > numbers[h]) {
                l = m + 1;
            } else if (numbers[m] < numbers[h]) {
                h = m;
            } else {
                h--;
            }
            m = l + (h - l) / 2;
        }
        return numbers[m];
    }

    /**
     * 判定字符串是否唯一
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public boolean isUnique(String astr) {
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < astr.length(); i++) {
            if (set.contains(astr.charAt(i))) {
                return false;
            }
            set.add(astr.charAt(i));
        }
        return true;
    }

    /**
     * 字符串反转
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public void reverseString(char[] s) {
        int i = 0, j = s.length - 1;
        while (i < j) {
            char tmp = s[i];
            s[i++] = s[j];
            s[j--] = tmp;
        }
    }

    /**
     * 堆排序
     */
    public int[] getLeastNumbers(int[] arr, int k) {
        return new int[2];
    }

    /**
     * 爬楼梯
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] res = new int[n];
        res[0] = 1;
        res[1] = 2;
        for (int i = 2; i < res.length; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }
        return res[n - 1];
    }

    /**
     * 67. 二进制求和
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public String addBinary(String a, String b) {
        char[] aChar = a.toCharArray();
        char[] bChar = b.toCharArray();
        StringBuilder builder = new StringBuilder();
        int flag = 0;
        for (int i = aChar.length - 1, j = bChar.length - 1; i >= 0 || j >= 0; i--, j--) {
            int res;
            if (i >= 0 && j >= 0) {
                res = aChar[i] + bChar[j] - '0' - '0';
            } else if (i >= 0) {
                res = aChar[i] - '0';
            } else {
                res = bChar[j] - '0';
            }
            builder.append((res + flag) % 2);
            flag = (res + flag) / 2;
        }
        if (flag == 0) return builder.reverse().toString();
        return builder.append("1").reverse().toString();
    }

    /**
     *
     */
//    public boolean isPalindrome(String s) {
//
//    }

    /**
     * 多数元素
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     * https://leetcode-cn.com/problems/majority-element/
     */
    public int majorityElement(int[] nums) {
        int res = 0;
        int flag = 0;
        for (int num : nums) {
            if (flag == 0) {
                res = num;
            }
            flag += (res == num) ? 1 : -1;
        }
        return res;
    }

    /**
     * 只出现一次的数字
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int num : nums) {
            res ^= num;
        }
        return res;
    }

    /**
     * 最后一个单词的长度
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int lengthOfLastWord(String s) {
        int length = 0;
        int i = 0;
        for (int j = 0; j < s.length(); j++) {
            if (Character.isUpperCase(s.charAt(j)) || Character.isLowerCase(s.charAt(j))) {
                i++;
            } else if (i != 0) {
                length = i;
                i = 0;
            }
        }
        if (i != 0) return i;
        return length;
    }

    /**
     * 买卖股票的最佳时机II
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int maxProfit1(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            max += Math.max(0, prices[i + 1] - prices[i]);
        }
        return max;
    }

    /**
     * 找出数组中重复的数字
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int findRepeatNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) {
                return num;
            }
            set.add(num);
        }
        return -1;
    }

    /**
     * 合并有序数组
     * 时间复杂度 o(m + n)
     * 空间复杂度 o(1)
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = m - 1;
        int p2 = n - 1;
        int p3 = m + n - 1;
        while (p1 >= 0 || p2 >= 0) {
            if (p1 >= 0 && p2 >= 0) {
                if (nums1[p1] >= nums2[p2]) {
                    nums1[p3--] = nums1[p1--];
                } else {
                    nums1[p3--] = nums2[p2--];
                }
            } else if (p1 >= 0) {
                nums1[p3--] = nums1[p1--];
            } else {
                nums1[p3--] = nums2[p2--];
            }
        }
    }

    /**
     * 加一
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int[] plusOne(int[] digits) {
        int tmp = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            digits[i] = digits[i] + tmp;
            tmp = digits[i] / 10;
            digits[i] %= 10;
            if (tmp == 0) {
                return digits;
            }
        }
        int[] res = new int[digits.length + 1];
        res[0] = 1;
        return res;
    }

    /**
     * 移动零
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public void moveZeroes(int[] nums) {
        for (int i = 0, j = 0; j < nums.length; j++) {
            if (nums[j] != 0) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
            }
        }
    }

    /**
     * 买卖股票
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public int maxProfit(int[] prices) {
        int max = 0;
        int curPrice = prices[0];
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] - curPrice > max) {
                max = prices[i] - curPrice;
            } else if (prices[i] - curPrice < 0) {
                curPrice = prices[i];
            }
        }
        return max;
    }

    /**
     * KMP算法
     * 时间复杂度 o(m + n)
     * 空间复杂度 o(n)
     */
    public int strStr(String haystack, String needle) {
        if (needle.length() == 0) return 0;
        int i = 0, j = 0;
        int[] next = getNext(needle);
        while (i < haystack.length() && j < needle.length()) {
            if (j == -1 || needle.charAt(j) == haystack.charAt(i)) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == needle.length()) return i - j;
        return -1;
    }

    private int[] getNext(String needle) {
        int[] next = new int[needle.length()];
        int k = -1;
        int j = 0;
        next[0] = -1;
        while (j < needle.length() - 1) {
            if (k == -1 || needle.charAt(j) == needle.charAt(k)) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    /**
     * 最大子序和
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public int maxSubArray(int[] nums) {
        if (nums.length == 0) return -1;
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] = nums[i] + Math.max(nums[i - 1], 0);
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    /**
     * 移除元素
     */
    public int removeElement(int[] nums, int val) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[j] != val) {
                nums[i++] = nums[j];
            }
        }
        return i;
    }

    /**
     * 删除有序数组中的重复项
     * 时间复杂度o(N)
     * 空间复杂度o(1)
     */
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int j = 0; j < nums.length; j++) {
            if (nums[i] != nums[j]) {
                nums[++i] = nums[j];
            }
        }
        return i + 1;
    }

    /**
     * 回文数
     * 时间复杂度o(logN)
     * 空间复杂度o(1)
     */
    public boolean isPalindrome(int x) {
        if (x == 0) return true;
        if (x < 0) return false;
        int tmp = 0;
        int res = x;
        while (res != 0) {
            tmp = tmp * 10 + res % 10;
            res /= 10;
        }
        return tmp == x;
    }

    /**
     * 有效的括号
     * ()[]{}
     * 时间复杂度: o(n)
     * 空间复杂度: o(n)
     */
    public boolean isValid(String s) {
        Map<Character, Character> map = new HashMap<Character, Character>() {
            {
                put(')', '(');
                put(']', '[');
                put('}', '{');
            }
        };
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c)) {
                if (stack.isEmpty()) return false;
                if (stack.pop() != map.get(c)) return false;
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    /**
     * 整数反转
     * 时间复杂度: o(logX)
     * 空间复杂度: o(1)
     */
    public int reverse(int x) {
        int flag;
        if (x >= 0) flag = 1;
        else flag = -1;
        x = Math.abs(x);
        int res = 0;
        while (x > 0) {
            if (res >= Integer.MAX_VALUE) return 0;
            if (res > Integer.MAX_VALUE / 10) return 0;
            res = res * 10 + x % 10;
            x /= 10;
        }
        return flag * res;
    }

    public ListNode reverseList(ListNode head) {
        //迭代
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;

        // 递归
//        if (head == null) {
//            return head;
//        }
//        ListNode newHead = reverseList(head.next);
//        head.next.next = head;
//        head.next = null;
//        return newHead;
    }

    private static class ListNode {
        public int val;
        public ListNode pre;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}

package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class JavaLtTest extends TestCase {
    @Test
    public void test() {
        reverse(1463847412);
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
     * 爬楼梯
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public int climbStairs(int n) {
        if (n == 1) return 1;
        if (n == 2) return 2;
        int[] res = new int[n];
        res[0] = 1;
        res[1] = 2;
        for (int i = 2; i < n; i++) {
            res[i] = res[i - 1] + res[i - 2];
        }
        return res[n - 1];
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

    /**
     * 两数之和
     */
    public int[] twoSum(int[] arrs, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arrs.length; i++) {
            if (map.containsKey(target - arrs[i])) {
                return new int[]{i, map.get(target - arrs[i])};
            } else {
                map.put(arrs[i], i);
            }
        }
        return new int[2];
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

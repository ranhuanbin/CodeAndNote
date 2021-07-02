package test;


import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class Test1 extends TestCase {

    @Test
    public void test() {
//        summaryRanges(new int[]{0, 1, 2, 4, 5, 7});
//        int[] res = getNext("ABCDABCDABDA");
//        int[] res1 = getNext("ABCDABCDABCA");
//        System.out.println(Arrays.toString(res));
//        System.out.println(Arrays.toString(res1));

        // -1,0,0,
        int kmp = KMPTest.KMP("ABCDABCEABDAFABCDABCDABDA", "ABCDEEEEEABCD");
        System.out.println(kmp);
    }

    public List<String> summaryRanges(int[] nums) {
        List<String> list = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] + 1 == nums[i + 1]) {
                builder.append(nums[i]);
            } else {
                builder.append(nums[i]);
                list.add(builder.toString());
                builder.setLength(0);
            }
            if (i == nums.length - 2) {
                builder.append(nums[i + 1]);
            }
        }
        return list;
    }

    public boolean detectCapitalUse(String word) {
        if (word == null || word.length() == 0) {
            return false;
        }
        int[] flags = new int[3];
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (i != 0) {
                if (ch >= 'A' && ch <= 'Z') {
                    if (flags[0] == 0 || flags[2] == 1) {
                        return false;
                    }
                    flags[1] = 1;
                } else if (ch >= 'a' && ch <= 'z') {
                    if (flags[1] != 0) {
                        return false;
                    }
                    flags[2] = 1;
                }
            } else {
                flags[0] = ch >= 'A' && ch <= 'Z' ? 1 : 0;
            }
        }
        return true;
    }

    /**
     * 前序遍历
     */
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int pre = -1;
        int min = Integer.MAX_VALUE;
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> list = new ArrayList<>();
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                if (pre == -1) {
                    pre = root.val;
                } else {
                    min = Math.min(min, Math.abs(pre - root.val));
                    pre = root.val;
                }
                list.add(root.val);
                System.out.println(root.val);
                root = root.right;
            }
        }
//        for (int i = 1; i < list.size(); i++) {
//            min = Math.min(min, Math.abs(list.get(i) - list.get(i - 1)));
//        }
        return min;
    }

    @Test
    public void testSingleNumber() {
        int[] res = getNext("ACAABAAABABAA");
        System.out.println("res = " + Arrays.toString(res));

//        int kmp = KMP("XXXAABABAAABABAAX", "AABABAAABABAA");
//        System.out.println("kmp = " + kmp);
    }

    public static int KMP1(String ts, String ps) {
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0; // 主串的位置
        int j = 0; // 模式串的位置
        int[] next = getNext(ps);
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) { // 当j为-1时，要移动的是i，当然j也要归0
                i++;
                j++;
            } else {
                // i不需要回溯了
                // i = i - j + 1;
                j = next[j]; // j回到指定位置
            }
        }
        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    /**
     * AAAABAAABABAA
     * <p>
     * j = 0, next[0] = -1;
     * j = 1, next[1] = 0;
     * j = 2, next[2] = 1;
     */
    public static int[] getNext2(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        System.out.println("getNext = " + Arrays.toString(next));
        return next;
    }

    public static int[] getNext1(String ps) {
        char[] p = ps.toCharArray();
        int[] next = new int[p.length];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < p.length - 1) {
            if (k == -1 || p[j] == p[k]) {
                if (p[++j] == p[++k]) { // 当两个字符相等时要跳过
                    next[j] = next[k];
                } else {
                    next[j] = k;
                }
            } else {
                k = next[k];
            }
        }
        System.out.println("getNext = " + Arrays.toString(next));
        return next;
    }


    public static int KMP(String ts, String ps) {
        int[] next = getNext(ps);
        char[] t = ts.toCharArray();
        char[] p = ps.toCharArray();
        int i = 0;
        int j = 0;
        while (i < t.length && j < p.length) {
            if (j == -1 || t[i] == p[j]) {
                i++;
                j++;
            } else {
                j = next[j];
            }
        }
        if (j == p.length) {
            return i - j;
        } else {
            return -1;
        }
    }

    public static int[] getNext(String ps) {
        char[] chs = ps.toCharArray();
        int[] next = new int[ps.length()];
        next[0] = -1;
        int j = 0;
        int k = -1;
        while (j < next.length - 1) {
            if (k == -1 || chs[j] == chs[k]) {
                next[++j] = ++k;
            } else {
                k = next[k];
            }
        }
        return next;
    }

    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null) {
            if (fast.val == slow.val) {
                return true;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return false;
    }

    public void merge(int[] A, int m, int[] B, int n) {
        int pa = m - 1;
        int pb = n - 1;
        int p = m + n - 1;
        for (int i = p; i >= 0; i--) {
            if (pa >= 0 && pb >= 0) {
                A[i] = A[pa] >= B[pb] ? A[pa--] : B[pb--];
            } else if (pa >= 0) {
                A[i] = A[pa--];
            } else {
                A[i] = B[pb--];
            }
        }
    }

    public void moveZeroes(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        for (int i = 0, j = 1; j < nums.length; j++) {
            if (nums[j] != 0) {
                nums[i] = nums[j];
                nums[j] = 0;
                i++;
            }
        }
    }

    public int[] plusOne(int[] digits) {
        if (digits == null || digits.length == 0) {
            return new int[0];
        }
        int digit = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            if (i == digits.length - 1) {
                int tmp = 1 + digits[i];
                digit = tmp / 10;
                digits[i] = tmp % 10;
            } else {
                int tmp = digit + digits[i];
                digit = tmp / 10;
                digits[i] = tmp % 10;
            }
        }
        if (digit == 1) {
            int[] res = new int[digits.length + 1];
            res[0] = 1;
            return res;
        }
        return digits;
    }

    // [7,1,5,3,6,4]
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int max = 0;
        int curPrice = 0;
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] - curPrice > max) {
                max = prices[i] - curPrice;
            } else if (prices[i] < curPrice) {
                curPrice = prices[i];
            }
        }
        return max;
    }

    public int singleNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry<Integer, Integer> next : entries) {
            if (next.getValue() == 1) {
                return next.getKey();
            }
        }
        return -1;
    }

    @Test
    public void testmaxSubArray() {
        System.out.println("MAX_VALUE = " + Integer.MAX_VALUE);
        System.out.println("MIN_VALUE = " + Integer.MIN_VALUE);
        System.out.println("MIN_VALUE % 10 = " + Integer.MIN_VALUE % 10);
    }

    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            nums[i] += Math.max(0, nums[i - 1]);
            max = Math.max(max, nums[i]);
        }
        return max;
    }

    @Test
    public void testStrToInt() {
        strToInt("4193 with words");
    }

    public int strToInt(String str) {
        char[] chars = str.trim().toCharArray();
        if (chars.length <= 0) {
            return 0;
        }
        int sign = 1;
        int i = 0;
        if (chars[0] == '-') {
            sign = -1;
            i = 1;
        } else if (chars[0] == '+') {
            i = 1;
        }
        int flag = Integer.MAX_VALUE / 10;
        int res = 0;
        for (int j = i; j < chars.length; j++) {
            if (res > flag || (res == flag && chars[j] > '7')) {
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            if (chars[j] >= '0' && chars[j] <= '9') {
                res = res * 10 + chars[j] - 48;
            } else {
                break;
            }
        }
        return sign * res;
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode pre = new ListNode(-1);
        ListNode head = new ListNode(-1);
        pre.next = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                head.next = l1;
                l1 = l1.next;
                head = head.next;
            } else {
                head.next = l2;
                l2 = l2.next;
                head = head.next;
            }
        }
        if (l1 == null) head.next = l2;
        if (l2 == null) head.next = l1;
        return pre.next.next;
    }

    private static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    private static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int val) {
            this.val = val;
        }
    }
}

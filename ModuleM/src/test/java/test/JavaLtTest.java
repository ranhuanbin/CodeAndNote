package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
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
 * 10.分发饼干 {@link #findContentChildren(int[], int[])}
 * <p>
 * <p>
 * 11.位运算, 需要关注char与int的关系
 * <p>      (1) 找不同 {@link #findTheDifference(String, String)}
 * 12.堆排序
 * <p>      (1) 最后一块石头的重量 {@link #lastStoneWeight(int[])}
 * 13.递归
 * <p>      (1) 链表反转 {@link #reverseList(ListNode)}
 * 5. 未做出来
 * (1) x的平方根 {@link #mySqrt(int)}
 * (2) 有多少小于当前数字的数字 {@link #smallerNumbersThanCurrent(int[])}
 * (3) 将数组分成和相等的三个部分 {@link #canThreePartsEqualSum(int[])}
 */
public class JavaLtTest extends TestCase {
    @Test
    public void test() {
//        reverse(1463847412);
//        lengthOfLastWord("Hello world");
//        canPlaceFlowers(new int[]{1, 0, 0, 0, 1}, 2);

//        distributeCandies(7, 4);

//        missingNumber(new int[]{3, 0, 1});
//        findTheDifference("abcd", "abcde");

//        lastStoneWeight(new int[]{2, 7, 4, 8, 1, 1});

    }

    /**
     * 只出现一次的数字
     * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-ii-lcof/
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int singleNumber1(int[] nums) {
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

    /**
     * 数组中出现次数超过一半的数字
     * https://leetcode-cn.com/problems/shu-zu-zhong-chu-xian-ci-shu-chao-guo-yi-ban-de-shu-zi-lcof/
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int majorityElement(int[] nums) {
        int x = 0, y = 0;
        for (int num : nums) {
            if (x == 0) {
                y = num;
            }
            x += (y == num ? 1 : -1);
        }
        return x > 0 ? y : -1;
    }

    /**
     * 合并两个排序的链表
     * https://leetcode-cn.com/problems/he-bing-liang-ge-pai-xu-de-lian-biao-lcof/
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode pre = new ListNode(-1);
        ListNode head = pre;
        pre.next = head;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                head.next = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                l2 = l2.next;
            }
            head = head.next;
        }
        if (l1 == null) {
            head.next = l2;
        } else {
            head.next = l1;
        }
        return pre.next;
    }

    /**
     * 反转链表
     * https://leetcode-cn.com/problems/fan-zhuan-lian-biao-lcof/
     */
    public ListNode reverseList(ListNode head) {
        // 迭代
//        if (head == null) return null;
//        ListNode pre = null;
//        ListNode cur = head;
//        while (cur != null) {
//            ListNode next = cur.next;
//            cur.next = pre;
//            pre = cur;
//            cur = next;
//        }
//        return pre;

        // 递归
        if (head == null || head.next == null) return head;
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public int[] reversePrint(ListNode head) {
        // 迭代
        List<Integer> arr = new ArrayList<>();
        if (head == null) return new int[0];
        ListNode pre = null;
        ListNode cur = head;
        while (cur != null) {
            arr.add(cur.val);
            ListNode next = cur.next;
            cur.next = pre;
            cur = next;
            pre = cur;
        }
        int[] res = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            res[i] = arr.get(arr.size() - i - 1);
        }
        return res;
    }

    /**
     * 存在重复元素 II
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                if ((i - map.get(nums[i])) <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);
        }
        return false;
    }

    /**
     * 独一无二的出现次数
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int j : arr) {
            map.put(j, map.getOrDefault(j, 0) + 1);
        }
        Set<Integer> set = new HashSet<>();
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry<Integer, Integer> next : entries) {
            if (set.contains(next.getValue())) {
                return false;
            }
            set.add(next.getValue());
        }
        return true;
    }

    public boolean backspaceCompare(String S, String T) {
        Stack<Character> stack1 = new Stack<>();
        Stack<Character> stack2 = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            if ('#' == S.charAt(i)) {
                if (!stack1.isEmpty()) {
                    stack1.pop();
                }
            } else {
                stack1.push(S.charAt(i));
            }
        }
        for (int i = 0; i < T.length(); i++) {
            if ('#' == T.charAt(i)) {
                if (!stack2.isEmpty()) {
                    stack2.pop();
                }
            } else {
                stack2.push(T.charAt(i));
            }
        }
        if (stack1.size() != stack2.size()) return false;
        while (!stack1.isEmpty()) {
            if (stack1.pop() != stack2.pop()) return false;
        }
        return true;
    }

    /**
     * 最后一块石头的重量
     * 时间复杂度 o(nlogn)
     * 空间复杂度 o(n)
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        for (int stone : stones) {
            queue.offer(stone);
        }
        while (queue.size() > 1) {
            int a = queue.poll();
            int b = queue.poll();
            if (a != b) {
                queue.offer(Math.abs(a - b));
            }
        }
        return queue.isEmpty() ? 0 : queue.poll();
    }

    /**
     * 最大连续 1 的个数
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int findMaxConsecutiveOnes(int[] nums) {
        int max = 0;
        int temp = 0;
        for (int num : nums) {
            if (num == 0) {
                if (temp > max) {
                    max = temp;
                }
                temp = 0;
            } else {
                temp++;
            }
        }
        return Math.max(max, temp);
    }

    /**
     * 拼写单词
     * 时间复杂度 o(n²)
     * 空间复杂度 o(m)
     */
    public int countCharacters(String[] words, String chars) {
        int count = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < chars.length(); i++) {
            map.put(chars.charAt(i), map.getOrDefault(chars.charAt(i), 0) + 1);
        }
        for (String s : words) {
            if (s.length() > chars.length()) {
                continue;
            }
            Map<Character, Integer> backMap = new HashMap<>(map);
            int innerCount = 0;
            for (int j = 0; j < s.length(); j++) {
                if (backMap.containsKey(s.charAt(j)) && backMap.getOrDefault(s.charAt(j), 0) > 0) {
                    innerCount++;
                    backMap.put(s.charAt(j), backMap.getOrDefault(s.charAt(j), 0) - 1);
                } else {
                    innerCount = 0;
                    break;
                }
            }
            count += innerCount;
        }
        return count;
    }

    /**
     * 将数组分成和相等的三个部分
     * https://leetcode-cn.com/problems/partition-array-into-three-parts-with-equal-sum/
     */
    public boolean canThreePartsEqualSum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return false;
    }

    /**
     * 翻转图像
     * 时间复杂度o(n²)
     * 空间复杂度o(1)
     */
    public int[][] flipAndInvertImage(int[][] image) {
        for (int i = 0; i < image.length; i++) {
            int l = 0, r = image[i].length - 1;
            while (l < r) {
                int temp = image[i][l];
                image[i][l] = image[i][r];
                image[i][r] = temp;
                l++;
                r--;
            }
        }
        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[i].length; j++) {
                image[i][j] ^= 1;
            }
        }
        return image;
    }

    /**
     * 找不同
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public char findTheDifference(String s, String t) {
        char res = 0;
        for (int i = 0; i < s.length(); i++) {
            res ^= s.charAt(i);
            res ^= t.charAt(i);
        }
        res ^= t.charAt(t.length() - 1);
        return res;
    }

    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < S.length(); i++) {
            if (stack.contains(S.charAt(i))) {
                while (stack.contains(S.charAt(i))) {
                    stack.pop();
                }
            } else {
                stack.push(S.charAt(i));
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        return builder.reverse().toString();
    }

    /**
     * 丢失的数字
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int missingNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
            res ^= i;
        }
        return res ^ nums.length;
    }

    /**
     * 单词规律
     * 时间复杂度 o(m)
     * 空间复杂度 o(m)
     */
    public boolean wordPattern(String pattern, String s) {
        String[] s1 = s.split(" ");
        if (pattern.length() != s1.length) {
            return false;
        }
        int i = 0;
        Map<String, Character> map = new HashMap<>();
        while (i < pattern.length()) {
            if (!map.containsKey(s1[i])) {
                if (map.containsValue(pattern.charAt(i))) {
                    return false;
                }
                map.put(s1[i], pattern.charAt(i));
            } else if (map.get(s1[i]) != pattern.charAt(i)) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * 字符串中的第一个唯一字符
     * 时间复杂度 o(n)
     * 空间复杂度 o(n)
     */
    public int firstUniqChar(String s) {
        int length = s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < length; i++) {
            if (map.containsKey(s.charAt(i))) {
                if (map.get(s.charAt(i)) == 1) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * 有多少小于当前数字的数字
     * https://leetcode-cn.com/problems/how-many-numbers-are-smaller-than-the-current-number/
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        return new int[0];
    }

    /**
     * 一维数组的动态和
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int[] runningSum(int[] nums) {
        int sum = 0;
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = nums[i] + sum;
            sum = res[i];
        }
        return res;
    }

    /**
     * 分发饼干
     * https://leetcode-cn.com/problems/assign-cookies/
     * 时间复杂度 o(mlogn + nlogm)
     * 空间复杂度 o(logm + logn)
     */
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int i = 0, j = 0;
        int count = 0;
        while (i < g.length && j < s.length) {
            if (s[j] >= g[i]) {
                count++;
                i++;
            }
            j++;
        }
        return count;
    }

    /**
     * 2的幂
     */
    public boolean isPowerOfTwo(int n) {
        if (n <= 0) return false;
        int l = 0;
        int r = 32;
        int m = (l + r) / 2;
        while (l < r) {
            if (Math.pow(2, m) == n) {
                return true;
            }
            if (Math.pow(2, m) > n) {
                r = m;
            } else {
                l = m + 1;
            }
            m = (l + r) / 2;
        }
        return false;
    }

    /**
     * 宝石与石头
     * 时间复杂度 o(m * n)
     * 空间复杂度 o(1)
     */
    public int numJewelsInStones(String J, String S) {
        int count = 0;
        for (int i = 0; i < J.length(); i++) {
            for (int j = 0; j < S.length(); j++) {
                if (J.charAt(i) == S.charAt(j)) count++;
            }
        }
        return count;
    }

    /**
     * 位1的个数
     */
    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 0) {
                count++;
            }
            n = n >>> 1;
        }
        return count;
    }

    /**
     * 分糖果
     * 时间复杂度 o(n)
     * 空间复杂度 o(1)
     */
    public int[] distributeCandies(int candies, int num_people) {
        int[] p = new int[num_people];
        int i = 0;
        int n = p.length;
        while (candies != 0) {
            if (candies <= i + 1) {
                p[i % n] += candies;
                return p;
            }
            candies -= (i + 1);
            p[i % n] += (i + 1);
            i++;
        }
        return p;
    }

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

    private static class ListNode {
        public int val;
        public ListNode pre;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}

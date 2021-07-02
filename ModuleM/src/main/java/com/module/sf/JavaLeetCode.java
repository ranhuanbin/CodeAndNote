package com.module.sf;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JavaLeetCode {
    public int strToInt(String str) {
        if (str == null || "".equals(str.trim())) {
            return 0;
        }
        int res = 0;
        for (int i = 0; i < str.length(); i++) {
            if (res == 0) {
                if ((str.charAt(i) >= '1' && str.charAt(i) <= '9')) {
                    res = str.charAt(i);
                } else if (str.charAt(i) == '-') {
                    res = -1;
                } else if (str.charAt(i) == '+') {
                    res = 1;
                } else if (str.charAt(i) != ' ') {
                    return 0;
                }
            } else if (res < 0) {
                if ((str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                    res = -1 * res * 10 + str.charAt(i) - 48;
                    res *= -1;
                } else {
                    return res;
                }
            } else {
                if ((str.charAt(i) >= '0' && str.charAt(i) <= '9')) {
                    res = res * 10 + str.charAt(i) - 48;
                } else {
                    return res;
                }
            }
        }
        return res;
    }

    public static int[] maxSlidingWindow(int[] nums, int k) {
        int length = nums.length - k + 1;
        int[] res = new int[length];
        int max;
        for (int i = 0; i <= nums.length - k; i++) {
            max = Integer.MIN_VALUE;
            for (int j = i; j < k + i; j++) {
                if (nums[j] > max) {
                    max = nums[j];
                }
            }
            res[i] = max;
        }
        return res;
    }

    public static void treeNodeTest() {
//        TreeNode node1 = new TreeNode(3);
//        TreeNode node2 = new TreeNode(9);
//        TreeNode node3 = new TreeNode(20);
//        TreeNode node4 = new TreeNode(15);
//        TreeNode node5 = new TreeNode(7);
//        node1.left = node2;
//        node1.right = node3;
//        node3.left = node4;
//        node3.right = node5;

//        TreeNode node1 = new TreeNode(1);
//        TreeNode node2 = new TreeNode(2);
//        TreeNode node3 = new TreeNode(3);
//        node1.right = node2;
//        node2.right = node3;

        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(2);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(3);
        TreeNode node5 = new TreeNode(3);
        TreeNode node6 = new TreeNode(3);
        TreeNode node7 = new TreeNode(3);
        TreeNode node8 = new TreeNode(4);
        TreeNode node9 = new TreeNode(4);
        TreeNode node10 = new TreeNode(4);
        TreeNode node11 = new TreeNode(4);
        TreeNode node12 = new TreeNode(4);
        TreeNode node13 = new TreeNode(4);
        TreeNode node14 = new TreeNode(5);
        TreeNode node15 = new TreeNode(5);
        node1.left = node2;
        node1.right = node3;
        node2.left = node4;
        node2.right = node5;
        node3.left = node6;
        node3.right = node7;
        node4.left = node8;
        node4.right = node9;
        node5.left = node10;
        node5.right = node11;
        node6.left = node12;
        node6.right = node13;
        node8.left = node14;
        node8.right = node15;

        isBalance(node1);
    }

    public static boolean isBalance(TreeNode root) {
        if (root == null) {
            return true;
        }
        int minDepth = Integer.MAX_VALUE;
        int maxDepth = 0;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
                if (node.left == null || node.right == null) {
                    if (maxDepth < minDepth) {
                        minDepth = maxDepth;
                    }
                }
                size--;
            }
            if (!queue.isEmpty()) {
                maxDepth++;
            }
        }
        return maxDepth <= minDepth - 1;
    }

    public static int[] printNumbers(int n) {
        int length = (int) Math.pow(10, n);
        int[] res = new int[length - 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        return res;
    }


    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    public int minArray(int[] numbers) {
        int min = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }
        return min;
    }

    public int missingNumber(int[] nums) {
        int res = nums.length;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
            res ^= i;
        }
        return res;
    }

    public int numWays(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }
        int[] nums = new int[n + 1];
        nums[0] = 1;
        nums[1] = 1;
        for (int i = 2; i <= n; i++) {
            nums[i] = nums[i - 1] + nums[i - 2];
        }
        return nums[n];
    }

    public int hammingWeight(int n) {
        int count = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                count++;
            }
            n >>>= 1;
        }
        return count;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isSymmetric(root, root);
    }

    public boolean isSymmetric(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left == null || right == null) {
            return false;
        }
        if (left.val != right.val) {
            return false;
        }
        return isSymmetric(left.left, right.right) || isSymmetric(left.right, right.left);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public char firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.getOrDefault(s.charAt(i), 0) > 1) {
                return s.charAt(i);
            }
        }
        return ' ';
    }

    public String reverseWords(String s) {
        StringBuilder builder = new StringBuilder();
        String[] s1 = s.split(" ");
        for (int i = s1.length - 1; i >= 0; i--) {
            if (!"".equals(s1[i].trim())) {
                builder.append(s1[i]).append(' ');
            }
        }
        return builder.toString().trim();
    }

    public static int majorityElement() {
        int[] nums = new int[]{1, 2, 1, 2, 1, 2, 3, 3};
        int x = 0, votes = 0;
        for (int num : nums) {
            if (votes == 0) x = num;
            votes += num == x ? 1 : -1;
        }
        return x;
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return new ArrayList<>();
        }
        List<List<Integer>> lists = new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int count = 0;
        while (!queue.isEmpty()) {
            List<Integer> list = new ArrayList<>();
            int size = queue.size();
            while (size != 0) {
                TreeNode poll = queue.poll();
                if (poll != null) {
                    if (count % 2 == 0) {
                        list.add(poll.val);
                    } else {
                        list.add(list.size() - 1, poll.val);
                    }
                    queue.offer(poll.left);
                    queue.offer(poll.right);
                }
                size--;
            }
            count = count + 1;
            lists.add(list);
        }
        return lists;
    }

    private Map<Integer, Integer> map;

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }
        return buildTree(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);
    }

    public TreeNode buildTree(int[] preorder, int[] inorder, int pre_left, int pre_right, int in_left, int in_right) {
        if (pre_left > pre_right) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[pre_left]);
        int order_root_index = map.get(preorder[pre_left]);
        int leftChildCount = order_root_index - in_left;
        root.left = buildTree(preorder, inorder, pre_left + 1, pre_left + leftChildCount, in_left, order_root_index - 1);
        root.right = buildTree(preorder, inorder, pre_left + leftChildCount + 1, pre_right, order_root_index + 1, in_right);
        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean isStraight() {
        int[] nums = new int[]{1, 2, 3, 4, 5};
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        int zeroCount = 0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (num != 0) {
                if (num > max) {
                    max = num;
                }
                if (num < min) {
                    min = num;
                }
                if (map.getOrDefault(num, 0) >= 1) {
                    return false;
                } else {
                    map.put(num, 1);
                }
            } else {
                zeroCount++;
            }
        }
        if (zeroCount == 0) {
            return max == min + 4;
        } else if (zeroCount == 1) {
            return max == min + 3 || max == min + 4;
        } else if (zeroCount == 2) {
            return max >= min + 2 && max <= min + 4;
        } else {
            return false;
        }
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode head = new ListNode(-1);
        ListNode cur = new ListNode(-1);
        head.next = cur;
        while (l1 != null && l2 != null) {
            if (l1.val >= l2.val) {
                cur.next = l2;
                l2 = l2.next;
            } else {
                cur.next = l1;
                l1 = l1.next;
            }
            cur = cur.next;
        }
        if (l1 == null) {
            cur.next = l2;
        } else {
            cur.next = l1;
        }
        return head.next;
    }

    public ListNode deleteNode(ListNode head, int val) {
        ListNode pre = new ListNode(-1);
        ListNode cur = new ListNode(-1);
        pre.next = cur;
        cur.next = head;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
            }
            cur = cur.next;
        }
        return pre.next.next;
    }

    // -9 ,-8 ,-7, -6, -5, -4, -3, -2, -1
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] + nums[i - 1] > nums[i]) {
                nums[i] += nums[i - 1];
            }
            if (nums[i] > max) {
                max = nums[i];
            }
        }
        return max;
    }

    public int[] exchange(int[] nums) {
        int i = 0;
        int j = nums.length - 1;
        while (i < j) {
            if (nums[i] % 2 == 0 && nums[j] % 2 == 1) {
                int tmp = nums[i];
                nums[i] = nums[j];
                nums[j] = tmp;
                i++;
                j--;
                continue;
            }
            if (nums[i] % 2 == 1) {
                i++;
            } else if (nums[j] % 2 == 0) {
                j--;
            }
        }
        return nums;
    }

    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        Map<Character, Character> map = new HashMap<>();
        map.put(']', '[');
        map.put('}', '{');
        map.put(')', '(');
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                stack.push(s.charAt(i));
            } else {
                if (stack.isEmpty()) {
                    return false;
                } else if (stack.pop() != s.charAt(i)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static String addBinary(String a, String b) {
        StringBuilder builder = new StringBuilder();
        int res = 0;
        int i = a.length() - 1, j = b.length() - 1;
        while (i >= 0 || j >= 0) {
            if (i >= 0) {
                res = res + a.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                res = res + b.charAt(j) - '0';
                j--;
            }
            builder.append(res % 2);
            res /= 2;
        }
        return res == 1 ? "1" + builder.reverse().toString() : builder.reverse().toString();
    }

    public static boolean isPalindrome(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= 'a' && c <= 'z')
                    || (c >= 'A' && c <= 'Z')
                    || (c >= '0' && c <= '9')) {
                builder.append(s.charAt(i));
            }
        }
        return builder.toString().equalsIgnoreCase(builder.reverse().toString());
    }

    public String longestCommonPrefix(String[] strs) {
        StringBuilder sBuilder = new StringBuilder();
        int minLength = Integer.MAX_VALUE;
        for (String str : strs) {
            if (str.length() < minLength) {
                minLength = str.length();
            }
        }
        int i = 0;
        while (i < minLength) {
            char val = strs[0].charAt(i);
            for (String str : strs) {
                if (val != str.charAt(i)) {
                    return sBuilder.toString();
                }
            }
            sBuilder.append(val);
            i++;
        }
        return sBuilder.toString();
    }


    public static void getLeastNumbers(int k) {
        int[] res = new int[k];
//        int[] num = {67, 45, 78, 64, 52, 11, 64, 55, 99, 11, 18};
//        int[] num = {0, 0, 1, 2, 4, 2, 2, 3, 1, 4};
        int[] num = {0, 1, 1, 2, 4, 4, 1, 3, 3, 2};
        getLeastNumbers(num, 0, num.length - 1, k, "始");
        System.arraycopy(num, 0, res, 0, k);
        Log.v("AndroidTest", "res = " + getNums(res));
    }

    public static void getLeastNumbers(int[] arr, int left, int right, int k, String tag) {
        if (left >= right) {
            return;
        }
        Log.v("AndroidTest", "-------------------------------------------------------start-------------------------------------------------------");
        Log.v("AndroidTest", "tag = " + tag + ", left = " + left + ", right = " + right + ", i = " + left + ", j = " + right + ", key = " + arr[left] + ", k = " + k + " | [" + getNums(arr) + "]");
        int i = left, j = right;
        int key = arr[left];
        while (i < j) {
            while (arr[j] >= key && i < j) {
                j--;
            }
            while (arr[i] <= key && i < j) {
                i++;
            }
            if (i < j) {
                swap(arr, i, j);
            }
        }
        arr[left] = arr[i];
        arr[i] = key;
        int num = i - left + 1;
        Log.v("AndroidTest", "tag = " + tag + ", left = " + left + ", right = " + right + ", i = " + i + ", j = " + j + ", key = " + key + ", k = " + k + ", num = " + num + " | [" + getNums(arr) + "]");
        Log.v("AndroidTest", "-------------------------------------------------------end-------------------------------------------------------");
        if (k == num) {
            return;
        } else if (k < num) {
            getLeastNumbers(arr, left, i - 1, k, "左");
        } else {
            getLeastNumbers(arr, i + 1, right, k - num, "右");
        }
    }

    private static String getNums(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i == arr.length - 1) {
                builder.append(arr[i]);
            } else {
                builder.append(arr[i]).append(" ");
            }
        }
        return builder.toString();
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }


    public static void quickSort() {
        int[] num = {67, 45, 78, 64, 52, 11, 64, 55, 99, 11, 18};
        quickSort(num, 0, num.length - 1);
        Log.v("AndroidTest", "aaaa");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int[] twoSum(int[] nums, int target) {
        if (nums.length < 2) {
            return new int[2];
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                return new int[]{i, map.get(target - nums[i])};
            } else {
                map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            }
        }
        return new int[2];
    }

    public ListNode reverseList(ListNode head) {
        // 循环
//        if (head == null) {
//            return null;
//        }
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
        if (head == null || head.next == null) {
            return head;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }

    public void test() {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2, null);
        executor.schedule(() -> {

        }, 10, TimeUnit.SECONDS);
    }

    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int row = 0, col = cols - 1;
        while (row < rows && col >= 0) {
            int res = matrix[row][col];
            if (res == target) {
                return true;
            } else if (res > target) {
                col--;
            } else {
                row++;
            }
        }
        return false;
    }

    private static void quickSort(int[] num, int left, int right) {
        // 如果left == right, 说明数组只有一个元素
        if (left == right) {
            return;
        }
        int i = left, j = right;
        int key = num[left];
        while (i < j) {
            while (num[j] >= key && (i < j)) {
                j--;
            }
            while (num[i] <= key && (i < j)) {
                i++;
            }
            if (i < j) {
                swap(num, i, j);
            }
        }
        swap(num, left, i);
        quickSort(num, left, i - 1);
        quickSort(num, i + 1, right);
    }

    public static List<List<Integer>> generate(int numRows) {
        List<List<Integer>> pList = new ArrayList<>();
        if (numRows == 1) {
            List<Integer> list = new ArrayList<>();
            list.add(1);
            pList.add(list);
            return pList;
        }
        for (int i = 0; i < numRows; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                if (j == 0 || j == i) {
                    list.add(1);
                } else {
                    List<Integer> list1 = pList.get(i - 1);
                    list.add(list1.get(j - 1) + list1.get(j));
                }
            }
            pList.add(list);
        }
        return pList;
    }

    public boolean isAnagram(String s, String t) {
        if (s == null && t == null) {
            return true;
        }
        if (s.equals(t)) {
            return true;
        }
        char[] res = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            res[s.charAt(i) - 'a']++;
            res[t.charAt(i) - 'a']--;
        }
        for (char re : res) {
            if (re != 0) {
                return false;
            }
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int longestPalindrome(String s) {
        Map<Character, Integer> map = new HashMap<>();
        int length = 0;
        for (char ch : s.toCharArray()) {
            map.put(ch, map.getOrDefault(ch, 0) + 1);
        }
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        Iterator<Map.Entry<Character, Integer>> iterator = entries.iterator();
        while (iterator.hasNext()) {
            Map.Entry<Character, Integer> next = iterator.next();
            if (next.getValue() == null) {
                continue;
            }
            int count = next.getValue();
            if (count % 2 == 0) {
                length += count;
                iterator.remove();
            } else {
                length += (count - 1);
            }
            Log.v("AndroidTest", "hasNext: " + iterator.hasNext());
        }
        return map.size() == 0 ? length : length + 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int findRepeatNumber(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.getOrDefault(num, 0) == 0) {
                map.put(num, map.getOrDefault(num, 0) + 1);
            } else {
                return num;
            }
        }
        return 0;
    }

    public ListNode getKthFromEnd(ListNode head, int k) {
        int step = 1;
        ListNode slow = head;
        ListNode fast = head;
        while (step != k) {
            fast = fast.next;
            step++;
        }
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static boolean containsDuplicate(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
            if (map.getOrDefault(num, 0) != 0) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        for (int num : nums1) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        for (int num : nums2) {
            if (map.getOrDefault(num, 0) > 0) {
                map.put(num, map.get(num) - 1);
                res.add(num);
            }
        }
        return res.stream().mapToInt(Integer::intValue).toArray();
    }

    public int[] runningSum(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }

    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> res = new ArrayList<>();
        int max = 0;
        for (int candy : candies) {
            if (candy > max) {
                max = candy;
            }
        }
        for (int candy : candies) {
            res.add(candy + extraCandies >= max);
        }
        return res;
    }

    public char findTheDifference(String s, String t) {
        char a = 0;
        for (int i = 0; i < s.length(); i++) {
            a = (char) (a ^ s.charAt(i) ^ t.charAt(i));
        }
        return (char) (a ^ t.charAt(t.length() - 1));
    }

    public boolean checkPossibility(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                if (count >= 1) {
                    return false;
                } else {
                    count++;
                }
            }
        }
        return count <= 1;
    }

    List<Integer> list = new ArrayList<>();

    public String replaceSpace(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                builder.append("%20");
            } else {
                builder.append(s.charAt(i));
            }
        }
        return builder.toString();
    }

    public int maxProfit(int[] prices) {
        int maxProfit = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] - prices[i] > 0) {
                maxProfit += (prices[i + 1] - prices[i]);
            }
        }
        return maxProfit;
    }

    public String removeDuplicates(String S) {
        int length = S.length();
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < length; i++) {
            if (stack.isEmpty() || stack.peek() != S.charAt(i)) {
                stack.push(S.charAt(i));
            } else {
                while (!stack.isEmpty() && stack.peek() == S.charAt(i)) {
                    stack.pop();
                }
            }
        }
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        return builder.reverse().toString();
    }

    public static int[] distributeCandies(int candies, int num_people) {
        int[] res = new int[num_people];
        int index = 0;
        while (candies != 0) {
            if (candies > index + 1) {
                res[index % num_people] += (index + 1);
                candies = candies - index - 1;
                index++;
            } else {
                res[index % num_people] += candies;
                candies = 0;
            }
        }
        return res;
    }

    static class ListNode {
        int val;
        ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
}

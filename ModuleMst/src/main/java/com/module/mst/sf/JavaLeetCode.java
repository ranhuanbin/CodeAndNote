package com.module.mst.sf;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;

public class JavaLeetCode {
    public static int longestPalindrome(String s) {
        char[] chs = new char[52];
        int total = 0;
        int flag = 0;
        for (int i = 0; i < s.length(); i++) {
            if (chs[s.charAt(i) - 'A'] == 1) {
                chs[s.charAt(i) - 'A'] = 0;
                total++;
                flag--;
            } else {
                chs[s.charAt(i) - 'A']++;
                flag++;
            }
        }
        return flag > 0 ? total * 2 + 1 : total * 2;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            if (map.get(nums[i]) != null && map.get(nums[i]) > nums.length / 2) {
                return nums[i];
            }
        }
        return 0;
    }


    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    public ListNode reverseList(ListNode head) {
//        ListNode cur = head;
//        ListNode pre = null;
//        while (cur != null) {
//            ListNode next = cur.next;
//            cur.next = pre;
//            pre = cur;
//            cur = next;
//        }
//        return pre;

        if (head == null) {
            return null;
        }
        ListNode newHead = reverseList(head.next);
        head.next.next = head;
        return newHead;
    }

    public static String reverseWords(String s) {
        s = s.trim();
        String[] strs = s.split(" ");
        StringBuilder sbuilder = new StringBuilder();
        for (int i = strs.length - 1; i >= 0; i--) {
            if (!"".equals(strs[i])) {
                sbuilder.append(strs[i].trim()).append(" ");
            }
        }
        return sbuilder.toString().trim();
    }

    public int minArray(int[] numbers) {
//        int min = Integer.MAX_VALUE;
//        for (int number : numbers) {
//            if (number < min) {
//                min = number;
//            }
//        }
//        return min;

        int i = 0;
        int j = numbers.length - 1;
        while (i < j) {
            int mid = (i + j) / 2;
            if (numbers[mid] > numbers[j]) {
                i = mid + 1;
            } else if (numbers[mid] < numbers[j]) {
                j = mid;
            } else {
                ;
            }
        }
        return numbers[i];
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


    static class ListNode {
        int val;
        ListNode next;
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

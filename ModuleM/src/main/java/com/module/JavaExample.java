package com.module;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.module.sf.KErChaShu;
import com.module.sf.LeetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class JavaExample {
    public static void test() {
        KErChaShu.TreeNode node1 = new KErChaShu.TreeNode(5);
        KErChaShu.TreeNode node2 = new KErChaShu.TreeNode(4);
        KErChaShu.TreeNode node3 = new KErChaShu.TreeNode(6);
        KErChaShu.TreeNode node4 = new KErChaShu.TreeNode(1);
        KErChaShu.TreeNode node5 = new KErChaShu.TreeNode(2);
        KErChaShu.TreeNode node6 = new KErChaShu.TreeNode(7);
        KErChaShu.TreeNode node7 = new KErChaShu.TreeNode(8);
        node1.setLeft(node2);
        node1.setRight(node3);

        node2.setLeft(node4);
        node2.setRight(node5);

        node3.setLeft(node6);
        node4.setRight(node7);
    }

    public static void method() {
        HashMap<Integer, Integer> map = new HashMap<>();
        Set<Integer> set = map.keySet();
        for (Integer key : set) {
            if (map.containsKey(key * 2) && !map.get(key).equals(map.get(2 * key))) {
                return;
            }
        }

        Map<Character, Character> map1 = new HashMap<Character, Character>();
        map1.put('}', '{');
        map1.put(']', '[');
        map1.put(')', '(');
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int firstUniqChar(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i)) && map.get(s.charAt(i)) == 1) {
                return i;
            }
        }
        return -1;
    }

    public char findTheDifference(String s, String t) {
        char res = 0;
        for (int i = 0; i < s.length(); i++) {
            res = (char) (res ^ s.charAt(i) ^ t.charAt(i));
        }
        return (char) (res ^ t.charAt(t.length() - 1));
    }

    public static class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public boolean wordPattern(String pattern, String s) {
        char[] chars = pattern.toCharArray();
        String[] s1 = s.split(" ");
        if (chars.length != s1.length) {
            return false;
        }
        Map<Character, String> map1 = new HashMap<>();
        Map<String, Character> map2 = new HashMap<>();
        for (int i = 0; i < chars.length; i++) {
            if (map1.containsKey(chars[i]) && !s1[i].equals(map1.get(chars[i]))) {
                return false;
            } else {
                map1.put(chars[i], s1[i]);
            }
            if (map2.containsKey(s1[i]) && map2.get(s1[i]) != chars[i]) {
                return false;
            } else {
                map2.put(s1[i], chars[i]);
            }
        }
        return true;
    }

    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> array = new ArrayList<Integer>();
        for (int i = 1; i < nums.length + 1; i++) {
            array.add(i);
        }
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) {
            int value = iterator.next();
            if (array.contains(value)) {
                array.remove(value);
            }
        }
        return array;
    }

    public String removeDuplicates(String S) {
        Stack<Character> stack = new Stack<>();
        int length = S.length();
        for (int i = 0; i < length; i++) {
            if (stack.isEmpty()) {
                stack.push(S.charAt(i));
            } else {
                while (!stack.isEmpty()) {
                    Character ch = stack.peek();
                    if (S.charAt(i) == ch) {
                        stack.pop();
                    } else {
                        stack.push(S.charAt(i));
                        break;
                    }
                }
            }
        }
        if (stack.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        while (!stack.isEmpty()) {
            builder.append(stack.pop());
        }
        return builder.toString();
    }

    public static void lastStoneWeight(int[] stones) {

        LeetCode.Companion.findMaxAverage(new int[]{0, 4, 0, 3, 2}, 1);
    }

    public boolean lemonadeChange(int[] bills) {
        int[] array = new int[]{0, 0};
        for (int bill : bills) {
            if (bill == 5) {
                array[0]++;
            } else if (bill == 10) {
                if (array[0] == 0) {
                    return false;
                }
                array[0]--;
                array[1]++;
            } else if (bill == 20) {
                if (array[0] == 0) {
                    return false;
                }
                if (array[1] == 0 && array[0] < 3) {
                    return false;
                }
                if (array[1] != 0) {
                    array[1]--;
                    array[0]--;
                } else {
                    array[0] -= 3;
                }
            }
        }
        return true;
    }

    public int[] reversePrint(ListNode head) {
        if (head == null) {
            return new int[0];
        }
        int length = 0;
        ListNode cur = head;
        ListNode pre = null;
        while (cur != null) {
            length++;
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        int[] res = new int[length];
        length = 0;
        while (pre != null) {
            res[length++] = pre.val;
            pre = pre.next;
        }
        return res;
    }

}

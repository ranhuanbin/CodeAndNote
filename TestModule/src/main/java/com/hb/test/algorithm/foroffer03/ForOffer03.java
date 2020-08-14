package com.hb.test.algorithm.foroffer03;

import com.hb.test.utils.LogUtils;

import java.util.Stack;

/**
 * 输入一个链表的头结点，从尾到头反过来打印出每个结点的值。
 */
public class ForOffer03 {
    public static final String TAG = "ForOffer03";

    public static class ListNode {
        int val;//节点的值
        ListNode next;//下一个节点
    }

    // 栈的方式
    public static void method1(ListNode root) {
        Stack<ListNode> listNodes = new Stack<>();
        while (root != null) {
            listNodes.push(root);
            root = root.next;
        }
        while (!listNodes.empty()) {
            LogUtils.v(TAG, ForOffer03.class, "val: " + listNodes.pop().val);
        }
    }

    // 递归的方式
    public static void method2(ListNode root) {
        if (root != null) {
            method2(root.next);
            LogUtils.v(TAG, ForOffer03.class, "val: " + root.val);
        }
    }
}

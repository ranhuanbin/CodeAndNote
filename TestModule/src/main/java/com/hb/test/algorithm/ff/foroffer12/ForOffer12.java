package com.hb.test.algorithm.ff.foroffer12;

import com.hb.test.algorithm.NodeList;

/**
 * 输入一个链表，输出该链表中倒数第k个结点。为了符合大多数人的习惯，本题从1开始计数，
 * 即链表的尾结点是倒数第1个结点．例如一个链表有6个结点，从头结点开始它们的值依次是1、2、3、4、5、6。这个链表的倒数第3个结点是值为4的结点。
 * <p>
 * 1-2-3-4-5-6-7-8-9-10  n(10)个节点, 倒数第k(3)个节点, 对应正数第(n-k+1)(8)个节点
 */
public class ForOffer12 {
    public static NodeList forOffer12(int k, NodeList root) {
        if (root == null || k < 1) {
            throw new IllegalArgumentException("input invalid");
        }
        int i = 1;
        NodeList node1 = root;
        while (i++ < k) {
            if (node1.next == null) {//说明k超过链表长度
                throw new IllegalArgumentException("k is over the length of NodeList");
            }
            node1 = node1.next;
        }
        NodeList node2 = root;
        while (node1.next != null) {
            node1 = node1.next;
            node2 = node2.next;
        }
        return node2;
    }

}


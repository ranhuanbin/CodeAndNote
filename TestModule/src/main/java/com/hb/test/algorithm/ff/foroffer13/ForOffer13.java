package com.hb.test.algorithm.ff.foroffer13;

import com.hb.test.algorithm.IFO;
import com.hb.test.algorithm.NodeList;


/**
 * 定义一个函数，输入一个链表的头结点，反转该链表并输出反转后链表的头结点。
 */
public class ForOffer13 extends IFO {
    public static final String TAG = "ForOffer13";

    public static void forOffer13Test() {
        NodeList node7 = new NodeList(7, null);
        NodeList node6 = new NodeList(6, node7);
        NodeList node5 = new NodeList(5, node6);
        NodeList node4 = new NodeList(4, node5);
        NodeList node3 = new NodeList(3, node4);
        NodeList node2 = new NodeList(2, node3);
        NodeList node1 = new NodeList(1, node2);
        digui(node1);
    }

    private static NodeList digui(NodeList cur) {
        if (cur == null || cur.next == null) {
            return cur;
        }
        NodeList next = cur.next;
        cur.next = null;
        NodeList node = digui(next);
        next.next = cur;
        return node;
    }

    private static NodeList xunhuan(NodeList root) {
        if (root == null || root.next == null) {
            return root;
        }
        NodeList pre = root;
        NodeList cur = root.next;
        NodeList next;
        while (cur != null) {
            next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }

        root.next = null;
        root = pre;
        return root;
    }
}

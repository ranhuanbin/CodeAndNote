package com.hb.test.algorithm.ff.foroffer14;

import com.hb.test.algorithm.NodeList;

/**
 * 输入两个递增排序的链表，合并这两个链表并使新链表中的结点仍然是按照递增排序的。
 */
public class ForOffer14 {
    public static void testForOffer14() {

    }

    private static NodeList xunhuan(NodeList root1, NodeList root2) {
        if (root1 == null && root2 == null) {
            throw new IllegalArgumentException("input invalid");
        }
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        NodeList newRoot = new NodeList();
        NodeList pointer = newRoot;
        while (root1 != null && root2 != null) {
            if (root1.value < root2.value) {
                pointer.next = root1;
                root1 = root1.next;
            } else {
                pointer.next = root2;
                root2 = root2.next;
            }
            pointer = pointer.next;
        }
        if (root1 == null) {
            pointer.next = root2;
        }
        if (root2 == null) {
            pointer.next = root1;
        }
        return newRoot.next;
    }
}

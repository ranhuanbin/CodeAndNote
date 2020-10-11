package com.hb.test.algorithm.lt.lt15;


/**
 * 给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。
 */
public class Lt15 {
    public static void testForLt15() {
        ListNode node5 = new ListNode(3, null);
        ListNode node4 = new ListNode(3, node5);
        ListNode node3 = new ListNode(2, node4);
        ListNode node2 = new ListNode(1, node3);
        ListNode node1 = new ListNode(1, node2);
        optDeleteDuplicates(node1);
    }

    public static ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode node = head;
        while (node.next != null) {
            if (node.val == node.next.val) {
                node.next = node.next.next;
            } else {
                node = node.next;
            }
        }
        return head;
    }

    public static ListNode optDeleteDuplicates(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode node = head, tmp = head;
        while (tmp != null) {
            if (node.val != tmp.val) {
                node.next = tmp;
                node = node.next;
            } else if (tmp.next == null) {
                node.next = null;
            }
            tmp = tmp.next;
        }
        return head;
    }


    private static class ListNode {
        int val;
        ListNode next;

        public ListNode() {

        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}

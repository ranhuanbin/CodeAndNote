package com.hb.test.algorithm.lt.lt06;

/**
 * 将两个升序链表合并为一个新的升序链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
 */
public class Lt06 {
    public static final String TAG = "Lt06";

//    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
//        if (l1 == null) {
//            return l2;
//        }
//        if (l2 == null) {
//            return l1;
//        }
//        ListNode head = new ListNode();
//        ListNode node = head;
//        while (l1 != null || l2 != null) {
//            if (l1 == null) {
//                node.next = l2;
//                break;
//            }
//            if( (l2 == null)) {
//                node.next = l1;
//                break;
//            }
//            if (l1.val > l2.val) {
//                node.next = l2;
//                l2 = l2.next;
//            } else {
//                node.next = l1;
//                l1 = l1.next;
//            }
//            node = node.next;
//        }
//        return head.next;
//    }

    /**
     * @param l1 1->2->4
     * @param l2 1->3->4
     */
    public static ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        if (l1.val > l2.val) {
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        } else {
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        }
    }

    private static class ListNode {
        int val;
        ListNode next;
    }
}

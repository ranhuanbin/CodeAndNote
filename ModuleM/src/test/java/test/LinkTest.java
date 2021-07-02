package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.Stack;

/**
 * 1. 哨兵模式
 * </>  (1){@link #deleteDuplicates(ListNode)} 与(2)和(3)不同
 * </>  (2){@link #deleteNode(ListNode, int)}
 * </>  (3){@link #mergeTwoLists(ListNode, ListNode)}
 * </>  为什么(1)与(2)、(3)不同呢? 因为(1)的head节点不会被修改, 所以只需要一个pre就可以了, 但是(2)和(3)的head可能会发生变化, 所以需要两个前置节点pre和cur
 */
public class LinkTest extends TestCase {
    @Test
    public void test() {
        ListNode n1 = new ListNode(1);
        ListNode n2 = new ListNode(1);
        ListNode n3 = new ListNode(2);
        n1.next = n2;
        n2.next = n3;
        deleteDuplicates(n1);
    }

    /**
     * 两两交换链表中的节点
     */
    public ListNode swapPairs(ListNode head) {
        /**
         * 递归: 三要素
         * 1. 出口
         * 2. 当前层需要考虑的事情
         * 3. 返回值
         */
        // 1. 出口
        if (head == null || head.next == null) return head;
        ListNode next = head.next;
        head.next = swapPairs(next.next);
        next.next = head;
        return next;
    }

    /**
     * 删除排序链表中的重复元素
     */
    public ListNode deleteDuplicates(ListNode head) {
        ListNode pre = new ListNode(-1);
        pre.next = head;
        while (head != null && head.next != null) {
            if (head.val == head.next.val) {
                head.next = head.next.next;
            } else {
                head = head.next;
            }
        }
        return pre.next;
    }

    /**
     * 链表的中间结点
     */
    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        if (fast == null) {
            return slow;
        }
        return slow.next;
    }

    /**
     * 二进制链表转整数
     * 时间复杂度: o(N)
     * 空间复杂度: o(1)
     */
    public int getDecimalValue(ListNode head) {
        int res = 0;
        while (head != null) {
            res = (res << 1) + head.val;
            head = head.next;
        }
        return res;
    }

    /**
     * 第K个节点
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        if (head == null) return null;
        int i = 0;
        ListNode fast = head;
        ListNode slow = head;
        while (i < k) {
            fast = fast.next;
            i++;
        }
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }

    /**
     * 回文链表
     * 时间复杂度: o(n)
     * 空间复杂度: o(n)
     */
    public boolean isPalindrome(ListNode head) {
        Stack<ListNode> stack = new Stack<>();
        ListNode cur = head;
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }

        while (head != null) {
            if (head.val != stack.pop().val) {
                return false;
            }
            head = head.next;
        }
        return true;
    }

    /**
     * 合并链表
     * 时间复杂度: o(m+n)
     * 空间复杂度: o(1)
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode pre = new ListNode(-1);
        ListNode cur = new ListNode(-1);
        pre.next = cur;
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                cur.next = l1;
                l1 = l1.next;
            } else {
                cur.next = l2;
                l2 = l2.next;
            }
            cur = cur.next;
        }
        if (l1 == null) cur.next = l2;
        if (l2 == null) cur.next = l1;
        return pre.next.next;
    }

    /**
     * 链表相交
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode head1 = headA;
        ListNode head2 = headB;

        while (headA != null && headB != null) {
            if (headA == headB) {
                return headA;
            }
            if (headA.next == null && headB.next == null) {
                return null;
            }
            headA = headA.next == null ? head1 : headA.next;
            headB = headB.next == null ? head2 : headB.next;
        }
        return null;
    }

    /**
     * 环形链表
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != slow) {
            if (fast == null || fast.next == null) {
                return false;
            }
            fast = fast.next.next;
            slow = slow.next;
        }
        return true;
    }

    /**
     * 反转链表
     */
    public int[] reversePrint(ListNode head) {
        return new int[0];
    }

    /**
     * 反转链表
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public ListNode reverseList(ListNode head) {
        // 反转
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

    /**
     * 删除链表中指定的节点
     * 时间复杂度: o(n)
     * 空间复杂度: o(1)
     */
    public ListNode deleteNode(ListNode head, int val) {
        if (head == null) return null;
        ListNode pre = new ListNode(-1);
        ListNode cur = new ListNode(-1);
        pre.next = cur;
        cur.next = head;
        while (cur.next != null) {
            if (cur.next.val == val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }
        }
        return pre.next.next;
    }

    private class ListNode {
        public int val;
        public ListNode next;

        public ListNode(int val) {
            this.val = val;
        }
    }
}

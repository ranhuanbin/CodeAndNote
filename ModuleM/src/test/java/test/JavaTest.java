package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class JavaTest extends TestCase {

    @Test
    public void test() {

    }

    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null) {
            return false;
        }

        int d1 = 0, d2 = 0;
        boolean f1 = false, f2 = false;
        int d = 0;
        TreeNode p1 = null, p2 = null;

        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            d++;
            while (size != 0) {
                root = queue.poll();
                if (root.val == x) {
                    d1 = d;
                    f1 = true;
                } else if (root.val == y) {
                    d2 = d;
                    f2 = true;
                }
                if (root.left != null) {
                    if (root.val == x) {
                        d1 = d;
                        f1 = true;
                        p1 = root;
                    }
                    queue.offer(root.left);
                }
                if (root.right != null) {
                    if (root.val == y) {
                        d2 = d;
                        f2 = true;
                        p2 = root;
                    }
                    queue.offer(root.right);
                }
                size--;
            }
        }
        if ((f1 && f2) && (p1 != p2)) {
            return d1 == d2;
        }
        return false;
    }

    //    @Test
//    public void test() {
//        int a = 1;
//        int b = 3;
//        System.out.println("a & b = " + (a & b));
//        System.out.println("a | b = " + (a | b));
//        System.out.println("a ^ b = " + (a ^ b));
//    }

    // [7,1,5,3,6,4]
//    @Test
//    public void maxProfit(int[] prices) {
//        if (prices == null || prices.length == 0) {
//            return;
//        }
//        int max = 0;
//        int curPrice = prices[0];
//        for (int i = 0; i < prices.length; i++) {
//            if (prices[i] - curPrice > max) {
//                max = prices[i] - curPrice;
//            } else if (prices[i] - curPrice < 0) {
//                curPrice = prices[i];
//            }
//        }
//        return;
//    }
//
//    @Test
//    public void test() {
//        TreeNode node1 = new TreeNode(3);
//        TreeNode node2 = new TreeNode(9);
//        TreeNode node3 = new TreeNode(20);
//        TreeNode node4 = new TreeNode(15);
//        TreeNode node5 = new TreeNode(7);
//        node1.left = node2;
//        node1.right = node3;
//        node3.left = node4;
//        node3.right = node5;
//        List<List<Integer>> lists = levelOrder(node1);
//    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> lists = new ArrayList<>();
        if (root == null) {
            return lists;
        }
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            LinkedList<Integer> list = new LinkedList<>();
            while (size != 0) {
                size--;
                TreeNode poll = queue.poll();
                if (poll == null) {
                    continue;
                }
                if (lists.size() % 2 == 0) {
                    list.addFirst(poll.val);
                } else {
                    list.addLast(poll.val);
                }
                if (poll.left != null)
                    queue.offer(poll.left);
                if (poll.right != null)
                    queue.offer(poll.right);
            }
            lists.add(list);
        }
        return lists;
    }

    private static class TreeNode {
        public TreeNode left;
        public TreeNode right;
        public int val;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    public TreeNode treeToDoublyList_2(TreeNode root) {
        if (root == null) {
            return root;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode head = null, tail = null;
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                if (tail == null) {
                    // 双向链表第一个阶段, head == tail
                    tail = root;
                    head = tail;
                } else {
                    tail.right = root;
                    root.left = tail;
                    tail = root;
                }
                root = root.right;
            }
        }
        head.left = tail;
        tail.right = head;
        return head;
    }

    /**
     * 二叉树中序遍历
     */
    @Test
    public void testTreeToDoubly2() {
        TreeNode node = new TreeNode(1);
        TreeNode node1 = treeToDoublyList_2(node);
        System.out.println("node1 = " + node1.val);
    }

    Node head, pre;

    @Test
    public void testTreeToDoubly1() {
        Node node = new Node(1);
        testTreeToDoubly1_(node);
        head.left = pre;
        pre.right = head;
        System.out.println(head);
    }

    public void testTreeToDoubly1_(Node node) {
        if (node == null) {
            return;
        }
        testTreeToDoubly1_(node.left);
        if (pre == null) {
            pre = node;
            head = pre;
        } else {
            pre.right = node;
            node.left = pre;
            pre = node;
        }
        testTreeToDoubly1_(node.right);
    }

    private static class Node {
        public int val;
        public Node left;
        public Node right;

        public Node(int val) {
            this.val = val;
        }
    }


}

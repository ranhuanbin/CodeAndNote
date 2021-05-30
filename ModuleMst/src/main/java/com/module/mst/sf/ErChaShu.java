package com.module.mst.sf;

import android.util.Log;

import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * {@link ErChaShu}
 * 1.递归翻转
 * 2.遍历翻转
 * <p>
 * 1. 中序遍历 {@link}
 */
public class ErChaShu {
    private static TreeNode root;

    /*** 初始化TreeNode */
    private static void init() {
        TreeNode[] node = new TreeNode[10];//以数组形式生成一棵完全二叉树
        for (int i = 0; i < 10; i++) {
            node[i] = new TreeNode(i);
        }
        for (int i = 0; i < 10; i++) {
            if (i * 2 + 1 < 10) {
                node[i].left = node[i * 2 + 1];
            }
            if (i * 2 + 2 < 10) {
                node[i].right = node[i * 2 + 2];
            }
        }
    }

    /*** 中序遍历(非递归) */
    public static void preOrder(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        System.out.print("[中序遍历]");
        while (root != null || !stack.isEmpty()) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            if (!stack.isEmpty()) {
                root = stack.pop();
                System.out.println(" " + root.value);
                root = root.right;
            }
        }
    }

    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        TreeNode(int value) {
            this.value = value;
        }
    }

    public static TreeNode invertTree1(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tmp = root.left;
        root.left = root.right;
        root.right = tmp;
        invertTree1(root.left);
        invertTree1(root.right);
        return root;
    }

    public static TreeNode invertTree2(TreeNode root) {
        LinkedBlockingQueue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            TreeNode poll = queue.poll();
            TreeNode tmp = poll.right;
            poll.right = poll.left;
            poll.left = tmp;
            if (poll.left != null) {
                queue.offer(poll.left);
            }
            if (poll.right != null) {
                queue.offer(poll.right);
            }
        }
        return root;
    }


    public static TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        // 深度遍历
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        root1.value = root1.value + root2.value;
        mergeTrees(root1.left, root2.left);
        mergeTrees(root1.right, root2.right);
        return root1;
    }

    public static void mergeTrees() {
        TreeNode root1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(2);
        TreeNode node4 = new TreeNode(5);
        root1.left = node2;
        root1.right = node3;
        node2.left = node4;

        TreeNode root2 = new TreeNode(2);
        TreeNode node5 = new TreeNode(1);
        TreeNode node6 = new TreeNode(3);
        TreeNode node7 = new TreeNode(4);
        TreeNode node8 = new TreeNode(5);
        root2.left = node5;
        root2.right = node6;
        node5.right = node7;
        node6.right = node8;

        TreeNode node = mergeTrees(root1, root2);
        Log.v("AndroidTest", "node");
    }
}

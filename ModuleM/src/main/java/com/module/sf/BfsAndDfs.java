package com.module.sf;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/*** 深度优先遍历与广度优先遍历 */
public class BfsAndDfs {
    public static void init() {
        TreeNode n1 = new TreeNode(1);
        TreeNode n2 = new TreeNode(2);
        TreeNode n3 = new TreeNode(3);
        TreeNode n4 = new TreeNode(4);
        TreeNode n5 = new TreeNode(5);
        TreeNode n6 = new TreeNode(6);
        TreeNode n7 = new TreeNode(7);
        n1.left = n2;
        n1.right = n3;
        n2.left = n4;
        n2.right = n5;
        n3.left = n6;
        n3.right = n7;
        n7.left = n6;
        n5.left = n4;
        n6.left = n5;
        depthSearch1(n1);
        Log.v("AndroidTest", "------------------------------------");
        broadSearch1(n1);
    }

    /*** 深度优先遍历(递归) */
    private static void depthSearch1(TreeNode node) {
        if (node == null) {
            return;
        }
        Log.v("AndroidTest", "left = " + node.left + ", right = " + node.right);
        depthSearch1(node.left);
        depthSearch1(node.right);
    }

    /*** 深度优先遍历(循环) */
    private static void depthSearch2(TreeNode node) {
        Stack<TreeNode> stack = new Stack<>();
        stack.push(node);
        List<Integer> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            node = stack.pop();
            if (list.contains(node.value)) {
                continue;
            }
            list.add(node.value);
            Log.v("AndroidTest", "深度优先遍历 当前节点 TreeNode = " + node.value);
            TreeNode right = node.right;
            if (right != null && !list.contains(right.value)) {
                stack.push(right);
            }
            TreeNode left = node.left;
            if (left != null && !list.contains(left.value)) {
                stack.push(left);
            }
        }
    }

    /*** 广度优先遍历(循环) */
    private static void broadSearch1(TreeNode node) {
        Queue<TreeNode> queue = new LinkedBlockingQueue<>();
        queue.offer(node);
        List<Integer> list = new ArrayList<>();
        while (!queue.isEmpty()) {
            node = queue.poll();
            if (list.contains(node.value)) {
                continue;
            }
            list.add(node.value);
            Log.v("AndroidTest", "广度优先遍历 当前节点 TreeNode = " + node.value);
            TreeNode left = node.left;
            if (left != null && !list.contains(left.value)) {
                queue.offer(left);
            }
            TreeNode right = node.right;
            if (right != null && !list.contains(right.value)) {
                queue.offer(right);
            }
        }
    }

    /*** 广度优先遍历(递归) */
    private static void broadSearch2(TreeNode node) {

    }

    private static class TreeNode {
        int value;
        TreeNode left;
        TreeNode right;

        public TreeNode(int value) {
            this.value = value;
        }

        public TreeNode(int value, TreeNode left, TreeNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }
    }
}

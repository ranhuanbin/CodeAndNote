package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.LinkedList;

public class TreeTest extends TestCase {
    @Test
    public void test() {

    }


    /**
     * 剑指 Offer 54. 二叉搜索树的第k大节点
     */
    public int kthLargest(TreeNode root, int k) {
        // 迭代(后序遍历)
//        if (root == null || k < 0) return 0;
//        Stack<TreeNode> stack = new Stack<>();
//        while (root != null || !stack.isEmpty()) {
//            while (root != null) {
//                stack.push(root);
//                root = root.right;
//            }
//            if (!stack.isEmpty()) {
//                root = stack.pop();
//                if (--k == 0) return root.val;
//                System.out.println("后序遍历");
//                root = root.left;
//            }
//        }
//        return 0;

        // 递归(后序遍历)
        if (root == null || k < 0) return 0;
        this.k = k;
        kthLargest(root);
        return res;
    }

    private int k = 0, res = 0;

    public void kthLargest(TreeNode root) {
        if (root == null) return;
        kthLargest(root.right);
        if (--k == 0) {
            res = root.val;
            return;
        }
        kthLargest(root.left);
    }

    /**
     * 二叉树的深度
     */
    public int maxDepth(TreeNode root) {
        // 递归
//        int maxDep = 0;
//        if (root == null) return 0;
//        LinkedList<TreeNode> queue = new LinkedList<>();
//        queue.offer(root);
//        while (!queue.isEmpty()) {
//            int size = queue.size();
//            while (size != 0) {
//                root = queue.poll();
//                if (root.left != null) queue.offer(root.left);
//                if (root.right != null) queue.offer(root.right);
//                size--;
//            }
//            maxDep++;
//        }
//        return maxDep;

        // 递归
        if (root == null) return 0;
        return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
    }

    /**
     * 二叉树的镜像
     */
    public TreeNode mirrorTree(TreeNode root) {
        // 递归
//        if (root == null) {
//            return root;
//        }
//        TreeNode left = root.left;
//        root.left = root.right;
//        root.right = left;
//        mirrorTree(root.left);
//        mirrorTree(root.right);
//        return root;

        // 迭代
        if (root == null) return null;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.push(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.pop();
            TreeNode tmp = node.left;
            node.left = node.right;
            node.right = tmp;
            if (node.left != null) queue.push(node.left);
            if (node.right != null) queue.push(node.right);
        }
        return root;
    }

    private static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;
    }
}

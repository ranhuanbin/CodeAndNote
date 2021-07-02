package test;

import junit.framework.TestCase;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 1. 将有序数组转换为二叉搜索树 {@link #sortedArrayToBST(int[])}
 */
public class TreeTest extends TestCase {
    @Test
    public void test() {

    }

    /**
     * 有序数组转换为二叉搜索树
     * https://leetcode-cn.com/problems/convert-sorted-array-to-binary-search-tree/
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return new TreeNode(-1);
    }


    int total = 0;

    /**
     * 计算整个树的坡度
     */
    public int findTilt(TreeNode root) {
        if (root == null) return 0;
        findTilt1(root);
        return total;
    }

    public int findTilt1(TreeNode root) {
        if (root == null) return 0;
        int left = findTilt1(root.left);
        int right = findTilt1(root.right);
        total += Math.abs(left - right);
        return left + right + root.val;
    }

    /**
     * 单值二叉树
     */
    public boolean isUnivalTree(TreeNode root) {
        if (root == null) return true;
        int value = root.val;
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            if (root.val != value) return false;
            if (root.left != null) queue.offer(root.left);
            if (root.right != null) queue.offer(root.right);
        }
        return true;
    }

    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) return 0;
        diameterOfBinaryTree1(root);
        return max;
    }

    /**
     * 最大直径
     */
    private int max = 0;

    public int diameterOfBinaryTree1(TreeNode root) {
        if (root == null) return 0;
        int leftDept = diameterOfBinaryTree1(root.left);
        int rightDept = diameterOfBinaryTree1(root.right);
        if (leftDept + rightDept + 1 > this.max) {
            max = leftDept + rightDept + 1;
        }
        return Math.max(leftDept, rightDept) + 1;
    }

    /**
     * 翻转二叉树
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) return null;

        TreeNode left = root.left;
        root.left = root.right;
        root.right = left;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }

    /**
     * 合并二叉树
     */
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {

        if (root1 == null) return root2;
        if (root2 == null) return root1;

        TreeNode mergeNode = new TreeNode(root1.val + root2.val);
        mergeNode.left = mergeTrees(root1.left, root2.left);
        mergeNode.right = mergeTrees(root1.right, root2.right);
        return mergeNode;
    }

    /**
     * 平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        return getDep(root) >= 0;
    }

    public int getDep(TreeNode root) {
        if (root == null) return 0;
        int leftHeight = getDep(root.left);
        int rightHeight = getDep(root.right);

        if (leftHeight == -1 || rightHeight == -1 || Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }


    /**
     * 镜像二叉树
     */
    public boolean isSymmetric(TreeNode root) {
        // 迭代
        if (root == null) return false;
        LinkedList<TreeNode> queue1 = new LinkedList<>();
        LinkedList<TreeNode> queue2 = new LinkedList<>();
        queue1.offer(root);
        queue2.offer(root);
        while (!queue1.isEmpty()) {
            TreeNode node1 = queue1.poll();
            TreeNode node2 = queue2.poll();
            if (node1.left != null && node2.right == null) {
                return false;
            }
            if (node1.left == null && node2.right != null) {
                return false;
            }
            if (node1.right != null && node2.left == null) {
                return false;
            }
            if (node1.right == null && node2.left != null) {
                return false;
            }
            if (node1.left != null && node2.right != null) {
                if (node1.left.val != node2.right.val) {
                    return false;
                }
                queue1.offer(node1.left);
                queue2.offer(node2.right);
            }
            if (node1.right != null && node2.left != null) {
                if (node1.right.val != node2.left.val) {
                    return false;
                }
                queue1.offer(node1.right);
                queue2.offer(node2.left);
            }
        }
        return true;
    }

    /**
     * 镜像二叉树: 递归
     */
    public boolean isSymmetric(TreeNode node1, TreeNode node2) {
        if (node1 == null && node2 == null) return true;
        if (node1 == null || node2 == null) return false;

        if (node1.val != node2.val) return false;

        return isSymmetric(node1.left, node2.right) && isSymmetric(node1.right, node2.left);

    }


    /**
     * 层次打印二叉树
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();
        LinkedList<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int size;
        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            size = queue.size();
            List<Integer> list = new ArrayList<>();
            while (size != 0) {
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) queue.offer(root.left);
                if (root.right != null) queue.offer(root.right);
                size--;
            }
            res.add(list);
        }
        return res;
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

        public TreeNode(int val) {
            this.val = val;
        }
    }
}

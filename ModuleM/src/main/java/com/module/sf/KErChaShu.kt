package com.module.sf

import android.util.Log
import java.lang.StringBuilder
import java.util.*

/**
 * 递归三要素:
 * 1. 确定递归函数的参数和返回值:
 *    确定哪些参数是递归的过程中需要处理的, 那么就在递归函数里加上这个参数, 并且还要明确每次递归的返回值是什么, 进而确定递归函数的返回类型
 * 2. 确定终止条件
 * 3. 确定单层递归的逻辑
 *    确定每一层递归需要处理的信息.
 * 1.先序遍历(递归)   [preOrderRe]
 * 2.中序遍历(递归)   [midOrderRe]
 * 3.后序遍历(递归)   [postOrderRe]
 *
 * 4.先序遍历(非递归) [preOrder]
 * 5.中序遍历(非递归) [midOrder]
 * 6.后序遍历(非递归)
 */
object KErChaShu {
    lateinit var sb1: StringBuilder
    lateinit var sb2: StringBuilder
    lateinit var sb3: StringBuilder
    fun method() {
        val node1 = TreeNode(5)
        val node2 = TreeNode(4)
        val node3 = TreeNode(6)
        val node4 = TreeNode(1)
        val node5 = TreeNode(2)
        val node6 = TreeNode(7)
        val node7 = TreeNode(8)
        node1.left = node2
        node1.right = node3

        node2.left = node4
        node2.right = node5

        node3.left = node6
        node3.right = node7

        sb1 = StringBuilder()
        sb2 = StringBuilder()
        sb3 = StringBuilder()

        preOrderRe(node1)
        Log.v("AndroidTest", "[前序遍历] res = $sb1")
        midOrderRe(node1)
        Log.v("AndroidTest", "[中序遍历] res = $sb2")
        postOrderRe(node1)
        Log.v("AndroidTest", "[后序遍历] res = $sb3")
    }

    /*** 先序遍历(递归) */
    private fun preOrderRe(root: TreeNode?) {
        if (root == null) {
            return
        }
        sb1.append(root.value).append(" ")
        preOrderRe(root.left)
        preOrderRe(root.right)
    }

    /*** 中序遍历(递归) */
    private fun midOrderRe(root: TreeNode?) {
        if (root == null) {
            return
        }
        midOrderRe(root.left)
        sb2.append(root.value).append(" ")
        midOrderRe(root.right)
    }

    /*** 后序遍历(递归) */
    private fun postOrderRe(root: TreeNode?) {
        if (root == null) {
            return
        }
        postOrderRe(root.left)
        postOrderRe(root.right)
        sb3.append(root.value).append(" ")
    }

    /*** 先序遍历(非递归) */
    private fun preOrder(root: TreeNode?) {
        var node = root
        val stack = Stack<TreeNode>()
        while (node != null || stack.isNotEmpty()) {
            while (node != null) {
                Log.v("AndroidTest", "node.value = ${node.value}")
                stack.push(node)
                node = node.left
            }
            if (stack.isNotEmpty()) {
                node = stack.pop()
                node = node.right
            }
        }
    }

    /*** 中序遍历(非递归) */
    private fun midOrder(root: TreeNode?) {
        var node = root
        val stack = Stack<TreeNode>()
        while (node != null || stack.isNotEmpty()) {
            while (node != null) {
                stack.push(node)
                node = node.left
            }
            if (stack.isNotEmpty()) {
                node = stack.pop()
                Log.v("AndroidTest", "node.value = ${node.value}")
                node = node.right
            }
        }
    }


    public class TreeNode constructor(var value: Int) {
        var left: TreeNode? = null
        var right: TreeNode? = null
    }


}
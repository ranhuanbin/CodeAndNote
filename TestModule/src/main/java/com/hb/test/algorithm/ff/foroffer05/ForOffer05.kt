package com.hb.test.algorithm.ff.foroffer05

import com.hb.test.algorithm.NodeList
import java.lang.IllegalArgumentException
import java.util.*

/**
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数appendTail 和deleteHead，分别完成在队列尾部插入结点和在队列头部删除结点的功能。
 */
class ForOffer05 {
    var appendStack = Stack<NodeList>()
    var deleteStack = Stack<NodeList>()
    fun appengTail(node: NodeList) {
        if (deleteStack.empty()) {
            appendStack.push(node)
            return
        }
        while (!deleteStack.empty()) {
            appendStack.push(deleteStack.pop())
        }
        appendStack.push(node)
    }

    fun deleteHead(): NodeList {
        if (!deleteStack.empty()) {
            return deleteStack.pop()
        }
        if (appendStack.empty()) {
            throw IllegalArgumentException("stack is empty")
        }
        while (!appendStack.empty()) {
            deleteStack.push(appendStack.pop())
        }
        return deleteStack.pop()
    }

}
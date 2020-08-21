package com.hb.test.algorithm.foroffer10;

import com.hb.test.utils.LogUtils;

/**
 * 给定单向链表的一个头指针和节点指针，定义一个函数在O(1)时间删除该节点。
 */
public class JavaForOffer10 {
    public static final String TAG = "JavaForOffer10";

    /**
     * @param head 输入头节点
     * @param node 目标节点
     * @return 返回删除目标节点之后的头节点
     */
    public static NodeList forOffer10(NodeList head, NodeList node) {
        if (head == null || head.next == null || node == null) {
            throw new IllegalArgumentException("input invalid");
        }
        if (head == node) {
            //目标节点为头节点, 删除头节点
            LogUtils.v(TAG, JavaForOffer10.class, head.toString());
            return head.next;
        }
        NodeList tmp = head;
        while (tmp.next != node) {
            tmp = tmp.next;
        }
        if (node.next == null) {//目标节点是尾结点
            tmp.next = null;
        } else {//为中间节点
            tmp.next = tmp.next.next;
        }
        return head;
    }

    public static class NodeList {
        public NodeList next;
        public int val;

        @Override
        public String toString() {
            return "NodeList{" +
                    "next=" + next +
                    ", val=" + val +
                    '}';
        }
    }
}

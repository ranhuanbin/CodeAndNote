package com.hb.test.algorithm.foroffer18;

import com.hb.test.utils.LogUtils;

import java.util.Stack;

/**
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈的最小数的min 函数。在该栈中，调用min、push 及pop的时间复杂度都是O(1)。
 */
public class ForOffer18 {
    public static final String TAG = "ForOffer18";
    private static Stack<Integer> stacks = new Stack<>();

    public static void testForOffer18(int type) {
        if (type == 0) {
            min();
        } else if (type == 1) {
            pop();
        } else {
            push(type);
            for (int next : stacks) {
                LogUtils.v(TAG, ForOffer18.class, "next: " + next);
            }
        }
    }

    private static void min() {
        int pop = stacks.pop();
        LogUtils.v(TAG, ForOffer18.class, "pop: " + pop);
    }

    private static void push(int val) {
        if (stacks.isEmpty()) {
            stacks.push(val);
        } else {
            int peek = stacks.peek();
            if (val <= peek) {
                stacks.push(val);
            } else {
                int tmpVal = stacks.pop();
                push(val);
                stacks.push(tmpVal);
            }
        }
    }

    private static void pop() {
        int pop = stacks.pop();
        LogUtils.v(TAG, ForOffer18.class, "pop: " + pop);
    }
}







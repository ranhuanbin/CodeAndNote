package com.module.mst.sf;

import android.util.Log;

import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

/**
 * 堆排序
 * 对于一个数组 aar[]
 * 大顶堆: aar[i] >= aar[2 * i + 1] && aar[i] >= aar[2 * i + 2]
 * 小顶堆: aar[i] <= aar[2 * i + 1] && aar[i] <= aar[2 * i + 2]
 * <p>
 * [20, 22, 21, 30, 32, 30, 28, 37, 32, 34]
 */
public class Heapsort {
    static PriorityQueue<Integer> priorityQueue;
    static Heapsort heapsort;

    public static void test() {
        heapsort = new Heapsort(true);
        priorityQueue = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            int i1 = new Random().nextInt(50);
            Log.v("AndroidTest", "i = " + i + ", i1 = " + i1);
            priorityQueue.add(i1);
            heapsort.add(i1);
        }
        Log.v("AndroidTest", "queue = " + Arrays.toString(priorityQueue.toArray()));
        Log.v("AndroidTest", "heapsort = " + Arrays.toString(heapsort.queue));
    }

    public static void pollTmp() {
        Integer poll = priorityQueue.poll();
        Log.v("AndroidTest", "poll = " + poll + "\npriorityQueue = " + Arrays.toString(priorityQueue.toArray()));
        heapsort.poll();
    }

    public int[] queue = new int[100];
    public int size = 0;
    /**
     * true: 最大堆
     * false: 最小堆
     */
    public boolean flag;

    public Heapsort(boolean flag) {
        this.flag = flag;
    }

    /**
     * true: 最小堆
     * false: 最大堆
     */
    public void add(int value) {
        if (size == 0) {
            queue[0] = value;
            size++;
            return;
        }
        int i = size;
        size = i + 1;
        while (i > 0) {
            int pIndex = (i - 1) >>> 1;
            int pValue = queue[pIndex];
            if (flag) {// 最小堆
                if (value >= pValue) {
                    break;
                }
            } else {// 最大堆
                if (value <= pValue) {
                    break;
                }
            }
            queue[i] = pValue;
            i = pIndex;
        }
        queue[i] = value;
    }

    public void poll() {
        if (size == 0) {
            return;
        }
        int res = queue[0];
        int i = queue[--size];
        queue[size] = 0;
        if (size > 0 && flag) {
            smallDump(0, i);
        } else if (size > 0) {
            bigDump(0, i);
        }
        Log.v("AndroidTest", "Heapsort res = " + res + "\nqueue = " + Arrays.toString(queue));
    }

    /**
     * 大顶堆
     */
    private void bigDump(int index, int value) {
        int half = size >>> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            int childValue = queue[childIndex];
            int rChildIndex = childIndex + 1;
            if (rChildIndex < size) {
                if (childValue < queue[rChildIndex]) {
                    childIndex = rChildIndex;
                    childValue = queue[childIndex];
                }
            }
            if (value >= childValue) {
                break;
            }
            queue[index] = childValue;
            index = childIndex;
        }
        queue[index] = value;
    }

    /**
     * 小顶堆
     */
    private void smallDump(int index, int value) {
        int half = size >>> 1;
        while (index < half) {
            int childIndex = (index << 1) + 1;
            int childValue = queue[childIndex];
            int rChildIndex = childIndex + 1;
            if (rChildIndex < size) {
                if (childValue > queue[rChildIndex]) {
                    childIndex = rChildIndex;
                    childValue = queue[childIndex];
                }
            }
            if (value <= childValue) {
                break;
            }
            queue[index] = childValue;
            index = childIndex;
        }
        queue[index] = value;
    }
}
//    public E poll() {
//        if (size == 0)
//            return null;
//        int s = --size;
//        modCount++;
//        E result = (E) queue[0];
//        E x = (E) queue[s];
//        queue[s] = null;
//        if (s != 0)
//            siftDown(0, x);
//        return result;
//    }
package com.hb.test.algorithm;

public class IForOffer {
    public static void checkNodeList(NodeList node) {
        if (node == null) {
            throw new IllegalArgumentException("input invalid");
        }
    }

    public static void checkInput(int[] arr, int k) {
        if (arr == null || arr.length == 0 || k <= 0) {
            throw new IllegalArgumentException("input invalid");
        }
    }

    public static void checkInput(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("input invalid");
        }
    }

    public static void checkInput(int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("input invalid");
        }
    }
}

package com.hb.test.algorithm;

public class NodeList {
    public NodeList next;
    public int value;

    public NodeList() {

    }

    public NodeList(int value, NodeList node) {
        this.value = value;
        next = node;
    }

    @Override
    public String toString() {
        return "NodeList{" +
                "value=" + value +
                '}';
    }


}

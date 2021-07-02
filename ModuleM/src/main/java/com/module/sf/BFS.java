package com.module.sf;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class BFS {

    public static void init() {
        HashMap<String, String[]> hashMap = new HashMap<>();
        hashMap.put("YOU", new String[]{"CLAIRE", "ALICE", "BOB"});
        hashMap.put("CLAIRE", new String[]{"YOU", "JONNY", "THOH"});
        hashMap.put("JONNY", new String[]{"CLAIRE"});
        hashMap.put("THOH", new String[]{"CLAIRE", "ANUJ"});
        hashMap.put("ALICE", new String[]{"YOU", "PEGGY"});
        hashMap.put("BOB", new String[]{"YOU", "PEGGY", "ANUJ"});
        hashMap.put("PEGGY", new String[]{"BOB", "ALICE"});
        hashMap.put("ANUJ", new String[]{"BOB", "THOH"});
        Node target = findTarget1("YOU", "ANUJ", hashMap);
        //打印出最短路径的各个节点信息
        printSearPath1(target);
    }

    /*** 打印出到达节点target所经过的各个节点信息 */
    static void printSearPath(Node target) {
        if (target != null) {
            Log.v("AndroidTest", "找到了目标节点 = " + target.id + "\n");

            List<Node> searchPath = new ArrayList<>();
            searchPath.add(target);
            Node node = target.parent;
            while (node != null) {
                searchPath.add(node);
                node = node.parent;
            }
            StringBuilder path = new StringBuilder();
            for (int i = searchPath.size() - 1; i >= 0; i--) {
                path.append(searchPath.get(i).id);
                if (i != 0) {
                    path.append("-->");
                }
            }
            Log.v("AndroidTest", "步数最短 = " + path);
        } else {
            Log.v("AndroidTest", "未找到了目标节点");
        }
    }

    static Node findTarget(String startId, String targetId, HashMap<String, String[]> map) {
        List<String> hasSearchList = new ArrayList<>();
        LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
        queue.offer(new Node(startId, null));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (hasSearchList.contains(node.id)) {
                continue;
            }
            Log.v("AndroidTest", "判断节点 = " + node.id + "\n");
            if (targetId.equals(node.id)) {
                return node;
            }
            hasSearchList.add(node.id);
            if (map.get(node.id) != null && map.get(node.id).length > 0) {
                for (String childId : map.get(node.id)) {
                    queue.offer(new Node(childId, node));
                }
            }
        }
        return null;
    }

    static Node findTarget1(String startId, String targetId, HashMap<String, String[]> map) {
        LinkedBlockingQueue<Node> queue = new LinkedBlockingQueue<>();
        List<String> list = new ArrayList<>();
        queue.offer(new Node(startId, null));
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (list.contains(node.id)) {
                continue;
            }
            if (targetId.equals(node.id)) {
                return node;
            }
            list.add(node.id);
            if (map.get(node.id) != null && map.get(node.id).length > 0) {
                for (String s : map.get(node.id)) {
                    queue.offer(new Node(s, node));
                }
            }
        }
        return null;
    }

    static void printSearPath1(Node target) {
        if (target == null) {
            return;
        }
        List<String> list = new ArrayList<>();
        while (target != null) {
            list.add(target.id);
            target = target.parent;
        }
        StringBuilder sBuilder = new StringBuilder();
        for (int i = list.size() - 1; i >= 0; i--) {
            sBuilder.append(list.get(i));
            if (i != 0) {
                sBuilder.append("->");
            }
        }
        Log.v("AndroidTest", "最短路径 = " + sBuilder.toString().trim());
    }

    public static class Node {
        public String id;
        public Node parent;

        public Node(String id, Node parent) {
            this.id = id;
            this.parent = parent;
        }
    }

}

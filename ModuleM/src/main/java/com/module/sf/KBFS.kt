//package com.module.mst
//
//import java.util.*
//import java.util.concurrent.LinkedBlockingQueue
//
//class BFS {
//    fun init() {
//        val hashMap: MutableMap<String, Array<String>> = HashMap()
//        hashMap["YOU"] = arrayOf("CLAIRE", "ALICE", "BOB")
//        hashMap["CLAIRE"] = arrayOf("YOU", "JONNY", "THON")
//        hashMap["JONNY"] = arrayOf("CLAIRE")
//        hashMap["THOH"] = arrayOf("CLAIRE")
//        hashMap["ALICE"] = arrayOf("YOU", "PEGGY")
//        hashMap["BOB"] = arrayOf("YOU", "PEGGY", "ANUJ")
//        hashMap["PEGGY"] = arrayOf("BOB", "ALICE")
//        hashMap["ANUJ"] = arrayOf("BOB")
//
//    }
//
//    fun findTarget(startId: String?, targetId: String, map: HashMap<String?, Array<String?>?>): Node? {
//        val hasSearchList: MutableList<String> = ArrayList()
//        val queue: LinkedBlockingQueue<Node> = LinkedBlockingQueue()
//        queue.offer(Node(startId!!, null))
//        while (!queue.isEmpty()) {
//            val node: Node = queue.poll()
//            if (hasSearchList.contains(node.id)) {
//                continue
//            }
//            print("判断节点:${node.id}".trimIndent())
//            if (targetId == node.id) {
//                return node
//            }
//            hasSearchList.add(node.id)
//            if (map[node.id] != null && map[node.id].size > 0) {
//                for (childId in map[node.id]!!) {
//                    queue.offer(Node(childId!!, node))
//                }
//            }
//        }
//        return null
//    }
//
//    class Node(var id: String, var parent: Node?)
//
//}
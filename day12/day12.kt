package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File


fun main() {
    class Node(name: String, nodeType: String) {
        val connections = mutableSetOf<Node>()
        val nodeType = nodeType
        val name = name

        fun addConnection(newNode: Node) {
            connections.add(newNode)
        }
    }

    val inputStrings = File("day12.txt").readLines()
    val nodes = mutableMapOf<String, Node>()
    for (line in inputStrings) {
        val splits = line.split("-")
        val lineNodes = mutableListOf<Node>()
        for (value in splits) {
            var currentNode = nodes.get(value)
            if (currentNode == null) {
                if (value == "start" || value == "end") {
                    currentNode = Node(value, value)
                } else if (value.uppercase() == value) {
                    currentNode = Node(value, "big")
                } else {
                    currentNode = Node(value, "small")
                }
                nodes.put(value, currentNode)
            }
            lineNodes.add(currentNode)
        }
        lineNodes[0].addConnection(lineNodes[1])
        lineNodes[1].addConnection(lineNodes[0])
    }
    // for ((name, node) in nodes) {
    //     println()
    //     println(name)
    //     for (con in node.connections) {
    //         println(con.name)
    //     }
    // }

    fun findPaths(nodeStart: Node, smallNodes: MutableSet<Node>): MutableList<MutableList<Node>> {
        if (nodeStart.nodeType == "end") {
            return mutableListOf(mutableListOf(nodeStart))
        }
        if (nodeStart.nodeType == "small") {
            smallNodes.add(nodeStart)
        }

       val paths = mutableListOf<MutableList<Node>>()
       for (nextNode in nodeStart.connections) {
            if (nextNode.nodeType == "start" || smallNodes.contains(nextNode)) {
                continue
            }
           paths += findPaths(nextNode, smallNodes.toMutableSet())
       }
       for (path in paths) {
           path.add(0, nodeStart)
       }
       return paths
    }

    val allPaths = findPaths(nodes.getValue("start"), mutableSetOf<Node>())
    fun makePathString(path: List<Node>): String {
        var stringPath = ""
        for (node in path){
            val name = node.name
            stringPath += "$name->"
        }
        return stringPath
    }
    // println(allPaths.map{makePathString(it)})
    val part1Ans = allPaths.size
    println("Part 1 = $part1Ans")
    
    fun findPathsPart2(nodeStart: Node, smallNodes: MutableMap<Node, Int>): MutableList<MutableList<Node>> {
        if (nodeStart.nodeType == "end") {
            return mutableListOf(mutableListOf(nodeStart))
        }
        val doubleSmall = (smallNodes.values.filter{ it > 1}).size > 0
        if (nodeStart.nodeType == "small") {
            val numVisit = smallNodes.get(nodeStart)
            if (numVisit == null) {
                smallNodes.put(nodeStart, 1)
            } else {
                if (doubleSmall) {
                    return mutableListOf()
                } else {
                    smallNodes.put(nodeStart, 2)
                }
            }
        }

       val paths = mutableListOf<MutableList<Node>>()
       for (nextNode in nodeStart.connections) {
            if (nextNode.nodeType == "start" || (doubleSmall && smallNodes.get(nextNode) == 1)) {
                continue
            }
           paths += findPathsPart2(nextNode, HashMap(smallNodes).toMutableMap())
       }
       for (path in paths) {
           path.add(0, nodeStart)
       }
       return paths
    }
    
    val part2Paths = findPathsPart2(nodes.getValue("start"), mutableMapOf())
    // println(part2Paths.map{makePathString(it)})
    val part2Ans = part2Paths.size
    println("Part 2 = $part2Ans")
}
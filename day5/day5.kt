
package org.kotlinlang.play
import kotlin.math.abs
import java.io.File

fun main() {
    val inputStrings = File("day5.txt").readLines()
    var positions: HashMap<String, Int> = hashMapOf()

    for (line in inputStrings) {
        val numbers = line.split(" -> ")
        val start = numbers[0].split(",").map { it.toInt() }
        val end = numbers[1].split(",").map { it.toInt() }
        // check for row or column match
        if (start[0] == end[0]) {
            // same row, increment column
            var startPoint: Int
            var endPoint: Int
            if (start[1] > end[1]) {
                startPoint = end[1]
                endPoint = start[1]
            } else {
                startPoint = start[1]
                endPoint = end[1]
            }
            val row = start[0]
            var key: String
            for (i in startPoint..endPoint) {
                key = "$row,$i"
                if (positions.containsKey(key)) {
                    val value = positions.get(key)
                    if (value != null) {
                        positions.put(key, value + 1)
                    }
                } else {
                    positions.put(key, 1)
                }
            }
        } else if (start[1] == end[1]) {
            // same row, increment column
            var startPoint: Int
            var endPoint: Int
            if (start[0] > end[0]) {
                startPoint = end[0]
                endPoint = start[0]
            } else {
                startPoint = start[0]
                endPoint = end[0]
            }
            val col = start[1]
            var key: String
            for (i in startPoint..endPoint) {
                key = "$i,$col"
                if (positions.containsKey(key)) {
                    val value = positions.get(key)
                    if (value != null) {
                        positions.put(key, value + 1)
                    }
                } else {
                    positions.put(key, 1)
                }
            }
        }
    }
    val part1Ans = positions.values.filter { it > 1 }.size
    println("Part 1 = $part1Ans")

    // Find diagonals with at 45 degrees

    for (line in inputStrings) {
        val numbers = line.split(" -> ")
        val start = numbers[0].split(",").map { it.toInt() }
        val end = numbers[1].split(",").map { it.toInt() }

        if (abs(start[0] - end[0]) == abs(start[1] - end[1])) {
            var rowIncrement = 1
            var colIncrement = 1

            if (start[0] > end[0]) {
                rowIncrement = -1
            }

            if (start[1] > end[1]) {
                colIncrement = -1
            }

            var rowPos = start[0]
            var colPos = start[1]
            var key: String
            while (rowPos != (end[0] + rowIncrement)) {
                key = (rowPos).toString() + "," + (colPos).toString()
                if (positions.containsKey(key)) {
                    val value = positions.get(key)
                    if (value != null) {
                        positions.put(key, value + 1)
                    }
                } else {
                    positions.put(key, 1)
                }
                rowPos += rowIncrement
                colPos += colIncrement
            }
        }
    }
    val part2Ans = positions.values.filter { it > 1 }.size
    println("Part 2 = $part2Ans")
}
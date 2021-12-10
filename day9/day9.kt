package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File


fun main() {
    val inputStrings = File("day9.txt").readLines()
    val lines: List<List<Int>> = inputStrings.map { it.toCharArray().map { it.toString().toInt() } }

    // Find < maps for all directions
    val lowPoints = mutableListOf<List<Int>>()
    for (i in 0..lines.size - 1) {
        for (j in 0..lines[i].size - 1) {
                val lessThanLeft = if (j==0) true else lines[i][j] < lines[i][j - 1]
                val lessThanRight = if (j==lines[i].size - 1) true else lines[i][j] < lines[i][j + 1]
                val lessThanUpper = if (i==0) true else lines[i][j] < lines[i - 1][j]
                val lessThanLower = if (i==lines.size - 1) true else lines[i][j] < lines[i + 1][j]
                if (lessThanLeft && lessThanRight && lessThanUpper && lessThanLower) {
                    lowPoints.add(listOf(j, i, lines[i][j]))
                }
        }
    }
    val part1Ans = lowPoints.map { it[2] + 1 }.sum()
    println("Part 1 = $part1Ans")

    fun findBasin(xPos: Int, yPos:Int, current: MutableSet<String>) {
        val index = "$xPos,$yPos"
        val basinPos = mutableListOf<List<Int>>()
        val inBasin = current.add(index)
        if (!inBasin) {
            return
        }
        // find values in horizontal
        if (xPos < lines[yPos].size - 1) {
            for (i in (xPos + 1)..lines[yPos].size - 1) {
                if (lines[yPos][i] != 9) {
                    basinPos.add(listOf(i, yPos))
                } else {
                    break
                }
            }
        }
        if (xPos > 0) {
            for (i in (xPos - 1) downTo 0) {
                if (lines[yPos][i] != 9) {
                    basinPos.add(listOf(i, yPos))
                } else {
                    break
                }
            }
        }
        if (yPos < lines.size - 1) {
            for (i in (yPos + 1)..lines.size - 1) {
                if (lines[i][xPos] != 9) {
                    basinPos.add(listOf(xPos, i))
                } else {
                    break
                }
            }
        }
        if (yPos > 0) {
            for (i in (yPos - 1) downTo 0) {
                if (lines[i][xPos] != 9) {
                    basinPos.add(listOf(xPos, i))
                } else {
                    break
                }
            }
        }
        for (pos in basinPos) {
            findBasin(pos[0], pos[1], current)
        }
    }

    val basins = mutableListOf<MutableSet<String>>()
    for (point in lowPoints) {
        val thisBasin = mutableSetOf<String>()
        findBasin(point[0], point[1], thisBasin)
        basins.add(thisBasin)
    }
    val maxBasin = basins.map { it.size }.sortedDescending().slice(setOf(0,1,2)).reduce {a,b -> a * b}
    println("Part 2 = $maxBasin")
}
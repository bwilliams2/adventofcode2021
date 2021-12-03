package org.kotlinlang.play

import java.io.File
import java.io.InputStream

fun main() {
    val inputStrings = File("day1.txt").readLines()
    val inputInt: List<Int> = inputStrings.map { it.toInt() }

    fun countIncreases(input: List<Int>): Int {
        var increased: Int = 0
        for (i in 1..input.size - 1) {
            if (input[i].compareTo(input[i-1]) > 0) {
                increased += 1
            }
        }
        return increased
    }
    val part1Ans = countIncreases(inputInt)
    println("Part 1: $part1Ans")

    var sums = mutableListOf<Int>()
    for (i in 2..inputInt.size - 1) {
        sums.add(inputInt.slice(i-2..i).sum())
    }
    val part2Ans = countIncreases(sums);
    println("Part 2: $part2Ans")


}

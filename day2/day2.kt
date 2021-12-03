package org.kotlinlang.play
import java.io.File

data class Instruction(val direction: String, val moves: Int)

fun main() {
    val inputStrings = File("day2.txt").readLines()

    var vertical: Int = 0
    var horizontal: Int = 0
    for (line in inputStrings) {
        val splits = line.split(" ")
        val direction = splits[0]
        val moves = splits[1].toInt()
        if (direction.equals("forward")) {
            horizontal += moves
        } else if (direction.equals("up")) {
            vertical -= moves
        } else if (direction.equals("down")) {
            vertical += moves
        }
    }
    val part1Ans = vertical * horizontal
    println("Part 1: $part1Ans")
    
    vertical = 0
    horizontal = 0
    var aim = 0
    for (line in inputStrings) {
        val splits = line.split(" ")
        val direction = splits[0]
        val moves = splits[1].toInt()
        if (direction.equals("forward")) {
            horizontal += moves
            vertical += moves * aim
        } else if (direction.equals("up")) {
            aim -= moves
        } else if (direction.equals("down")) {
            aim += moves
        }
    }
    val part2Ans = vertical * horizontal
    println("Part 2: $part2Ans")
}
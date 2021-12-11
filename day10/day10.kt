package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File


fun main() {
    val inputStrings = File("day10.txt").readLines()

    var brackets = mapOf<String, String>(
        "{" to "}",
        "(" to ")",
        "[" to "]",
        "<" to ">",
    )

    val closeBrackets = mutableMapOf<String, String>()
    for ((key, value) in brackets) {
        closeBrackets.put(value, key)
    }

    val bracketScore = mapOf<String, Int>(
        ")" to 3,
        "]" to 57,
        "}" to 1197,
        ">" to 25137
    )

    data class LineState(val state: String, val line: List<String>, val char: String, val score: Int)

    fun processLine(line: String): LineState {
        val lineArray = line.toCharArray().map{ it.toString() }
        val opening = mutableListOf<String>()
        var state = "valid"
        var illegalChar = ""
        var score = 0
        for (char in lineArray) {
            val openingChar = brackets.get(char)
            if (openingChar != null) {
                opening.add(char)
            } else {
                val expectedClosing = brackets.get(opening.last())
                if (char == expectedClosing) {
                    opening.removeLast()
                } else {
                   state = "corrupted" 
                   illegalChar = char
                   score = bracketScore.getValue(char)
                   break
                }
            }
        }
        if (state != "corrupted" && opening.size != 0) {
            state = "incomplete"
        }
        return LineState(state, opening, illegalChar, score) 
    }

    val states = mutableListOf<LineState>()
    for (line in inputStrings) {
        states.add(processLine(line))
    }
    val part1Ans = states.map{ it.score }.sum()
    println("Part 1 = $part1Ans")

    val closingScores = mapOf(
        ")" to 1,
        "]" to 2,
        "}" to 3,
        ">" to 4
    )
    val incomplete = states.filter{ it.state.equals("incomplete") }
    
    fun findComplete(lineState: LineState): Double {
        val closing = mutableListOf<String>()
        var score: Double = 0.0
        for (char in lineState.line.reversed()) {
            val expectedClosing = brackets.getValue(char)
            closing.add(expectedClosing)
            score *= 5
            score += closingScores.getValue(expectedClosing)
        }    
        return score
    }

    val scores = incomplete.map{ findComplete(it) }.sorted()
    val middle = (scores.size / 2)
    val part2Ans = scores[middle]
    println("Part 2 = $part2Ans")


}
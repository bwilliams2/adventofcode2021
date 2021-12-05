
package org.kotlinlang.play
import java.io.File

class Board(values: List<List<Int>>) {
    val original = values
    var matches = Array<Array<Boolean>>(original.size) {_ -> Array<Boolean>(original[0].size) {_ -> false}}
    var rowMatches = Array<Int>(original.size) {_ -> 0}
    var colMatches = Array<Int>(original[0].size) {_ -> 0}
    var hasWon = false

    fun checkNumber(number: Int): Boolean {
        for (i in 0..original.size - 1) {
            for (n in 0..original[i].size - 1) {
                if (number == original[i][n]) {
                    matches[i][n] = true
                    rowMatches[i] += 1
                    colMatches[n] += 1
                    if (colMatches[n] == rowMatches.size) {
                        hasWon = true
                    }
                    if (rowMatches[i] == colMatches.size) {
                        hasWon = true
                    }
                }
            }
        }
        return hasWon
    }

    fun sumUnMarked(): Int {
        var totalSum = 0
        for (i in 0..original.size - 1) {
            for (n in 0..original[i].size - 1) {
                if (!matches[i][n]) {
                    totalSum += original[i][n]
                }
            }
        }
        return totalSum
    }
}


fun main() {
    val inputStrings = File("day4.txt").readLines()
    val boards = mutableListOf<Board>()
    
    var collection = mutableListOf<List<Int>>()
    for (i  in 1..inputStrings.size - 1) {
        val line: String = inputStrings[i]
        if (!line.equals("")) {
            collection.add(line.trim().split("\\s+".toRegex()).map {it.toInt()})
        }
        if (i == inputStrings.size - 1 || inputStrings[i+1].equals("")) {
            boards.add(Board(collection))
            collection = mutableListOf<List<Int>>()
        }
    }

    var calledNumbers = inputStrings[0].split(",").map {it.toInt()}

    var winners = mutableListOf<Board>()

    var n: Int = 0
    var isWinner: Boolean
    while (winners.size == 0 && n < calledNumbers.size) {
        for (board in boards) {
            isWinner = board.checkNumber(calledNumbers[n])
            if (isWinner) {
                winners.add(board)
            }
        }
        n += 1
    }

    val newBoards = boards.map {Board(it.original)}
    val part1Ans = winners[0].sumUnMarked() * calledNumbers[n - 1]
    println("Part 1 = $part1Ans")
    n = 0
    var lastNumber: Int = 0
    while (n < calledNumbers.size) {
        for (board in newBoards) {
            if (board.hasWon) {
                continue
            }
            isWinner = board.checkNumber(calledNumbers[n])
            if (isWinner) {
                lastNumber = calledNumbers[n]
                winners.add(board)
            }
        }
        n += 1
    }
    val part2Ans = winners[winners.size-1].sumUnMarked() * lastNumber
    println("Part 2 = $part2Ans")
}
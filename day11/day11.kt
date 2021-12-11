package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File


fun main() {
    val inputStrings = File("day11test.txt").readLines()
    var octopi: MutableList<MutableList<Int>> = inputStrings.map { it.toCharArray().map { it.toString().toInt() }.toMutableList() }.toMutableList()
    // val totalFlashes: Array<Array<Int>> = Array<Array<Int>>(octopi.size) { _ -> Array<Int>(octopi[0].size) {_ -> 0}}

    val xMax = octopi[0].size - 1
    val yMax = octopi.size - 1

    val maxSteps = 100
    var steps = 0
    var totalFlashes: Long = 0

    data class FlashPosition(val x: Int, val y: Int)

    fun centerFlash(flashPos: FlashPosition, stepFlashes: Array<Array<Int>>) {
        val readyToFlash = mutableListOf<FlashPosition>()
        val xPos = flashPos.x
        val yPos = flashPos.y
        if (stepFlashes[yPos][xPos] == 0) {
            totalFlashes += 1
            stepFlashes[yPos][xPos] = 1
            val xPosLow = if (xPos == 0) 0 else xPos - 1
            val xPosHigh = if (xPos == xMax) xMax else xPos + 1
            val yPosLow = if (yPos == 0) 0 else yPos - 1
            val yPosHigh = if (yPos == yMax) yMax else yPos + 1
            for (x in xPosLow..xPosHigh) {
                for (y in yPosLow.. yPosHigh) {
                    octopi[y][x] += 1
                    if (octopi[y][x] > 9) {
                        readyToFlash.add(FlashPosition(x, y))
                    }
                }
            }
            for (flash in readyToFlash) {
                centerFlash(flash, stepFlashes)
            }
        }
    }

    while (steps < maxSteps) {
        val readyToFlash = mutableListOf<FlashPosition>()
        for (x in 0..xMax) {
            for (y in 0.. yMax) {
                octopi[y][x] += 1
                if (octopi[y][x] > 9) {
                    readyToFlash.add(FlashPosition(x, y))
                }
            }
        }
        val stepFlashes: Array<Array<Int>> = Array<Array<Int>>(octopi.size) { _ -> Array<Int>(octopi[0].size) {_ -> 0}}
        for (flash in readyToFlash) {
            centerFlash(flash, stepFlashes)
        }

        for (x in 0..xMax) {
            for (y in 0.. yMax) {
                if (stepFlashes[y][x] == 1) {
                    octopi[y][x] = 0
                }
            }
        }

        steps += 1
    }

    println("Part 1 = $totalFlashes")


    octopi = inputStrings.map { it.toCharArray().map { it.toString().toInt() }.toMutableList() }.toMutableList()
    steps = 0

    while (true) {
        val readyToFlash = mutableListOf<FlashPosition>()
        for (x in 0..xMax) {
            for (y in 0.. yMax) {
                octopi[y][x] += 1
                if (octopi[y][x] > 9) {
                    readyToFlash.add(FlashPosition(x, y))
                }
            }
        }
        val stepFlashes: Array<Array<Int>> = Array<Array<Int>>(octopi.size) { _ -> Array<Int>(octopi[0].size) {_ -> 0}}
        for (flash in readyToFlash) {
            centerFlash(flash, stepFlashes)
        }

        for (x in 0..xMax) {
            for (y in 0.. yMax) {
                if (stepFlashes[y][x] == 1) {
                    octopi[y][x] = 0
                }
            }
        }

        val allFlashes = stepFlashes.map { it.sum() }.sum()
        
        steps += 1
       
        if (allFlashes == (xMax + 1) * (yMax + 1)) {
            break
        }

    }

    println("Part 2 = $steps")
}
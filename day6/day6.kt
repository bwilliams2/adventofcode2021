package org.kotlinlang.play
import kotlin.math.abs
import kotlin.collections.sum
import java.io.File

fun main() {
    val inputStrings = File("day6.txt").readLines()
    val fishes: List<Int> = inputStrings[0].split(",").map { it.toInt() }
    var fishDays = Array<Long>(9) {_ -> 0L}
    for (fishDay in fishes) {
        fishDays[fishDay] += 1L
    }
    for (i in 1..80) {

        val zeroDay = fishDays[0]
        for (n in 0..7) {
            fishDays[n] = fishDays[n+1]
        }
        fishDays[8] = zeroDay
        fishDays[6] += zeroDay
    }
    val part1Ans = fishDays.sum()
    println("Part 1 = $part1Ans")
    
    fishDays = Array<Long>(9) {_ -> 0L}
    for (fishDay in fishes) {
        fishDays[fishDay] += 1L
    }
    for (i in 1..256) {

        val zeroDay = fishDays[0]
        for (n in 0..7) {
            fishDays[n] = fishDays[n+1]
        }
        fishDays[8] = zeroDay
        fishDays[6] += zeroDay
    }
    val part2Ans = fishDays.sum()
    println("Part 2 = $part2Ans")
}
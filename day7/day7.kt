package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File

fun main() {
    val inputStrings = File("day7.txt").readLines()
    val crabs: List<Int> = inputStrings[0].split(",").map { it.toInt() }
    val maxCrabs = crabs.maxOrNull()
    if (maxCrabs == null){
        throw Exception("max is null")
    }
    fun findCosts(costFunction: (crab: Int, ind: Int) -> Int): Int {
        var minimumFuel = crabs.map { costFunction(it, 0) }.sum()
        // var minimumPosition = maxCrabs + 1
        for (i in 1..maxCrabs) {
            var fuelCosts = crabs.map { costFunction(it, i) }.sum()
            if (fuelCosts < minimumFuel) {
                // minimumPosition = i
                minimumFuel = fuelCosts
            }
        }
        return minimumFuel
    }
    val part1Cost = {crab: Int, ind: Int -> abs(crab-ind)}
    val part1Ans = findCosts(part1Cost)
    println("Part 1 = $part1Ans")

    fun part2Cost(crab: Int, ind: Int): Int {
        var diff = abs(crab - ind)
        return (diff * (diff + 1))/2
    }
    val part2Ans = findCosts({crab, ind -> part2Cost(crab, ind)})
    println("Part 2 = $part2Ans")
}
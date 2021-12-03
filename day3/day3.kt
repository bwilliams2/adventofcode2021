package org.kotlinlang.play
import java.io.File

fun main() {
    val inputStrings = File("day3.txt").readLines()
    val intLists = inputStrings.map { (it.toCharArray().toTypedArray()).map {it.toString().toInt()} }

    fun countOnes(inputList: List<List<Int>>): Array<Int> {
        var ones = Array<Int>(inputList[0].size) {_ -> 0}
        for (num in inputList) {
            for (i in 0..num.size-1) {
                if (num[i].equals(1)) {
                    ones[i] += 1
                }
            }
        }
        return ones
    }

    val ones = countOnes(intLists)
    val listSize = intLists.size / 2
    val gammaRate = ones.map { if (it.compareTo(listSize) > 0) 1 else 0}
    val epsilonRate = gammaRate.map {if (it.equals(0)) 1 else 0 }
    val epsilonRateDec = epsilonRate.map { it.toString() }.joinToString("").toInt(2)
    val gammaRateDec = gammaRate.map { it.toString() }.joinToString("").toInt(2)
    val part1Ans = epsilonRateDec * gammaRateDec
    println("part1Ans = $part1Ans")


    fun findVal(condition: (Int, Double) -> Int): List<Int> {
        var tempList = intLists.map { it }
        var bitPosition = 0
        while (tempList.size > 1 && bitPosition < tempList[0].size) {
            val tempOnes = countOnes(tempList)
            val tempListSize = tempList.size.toFloat() / 2.0
            val modes = tempOnes.map { condition(it, tempListSize) }
            val gammaVal = modes[bitPosition]
            var keepInds: MutableList<Int> = mutableListOf()
            for (i in 0..tempList.size-1) {
                if (tempList[i][bitPosition] == gammaVal) {
                    keepInds.add(i)
                }
            }
            tempList = tempList.slice(keepInds.toSet())
            bitPosition += 1
        }
        return tempList[0]
    }
    val o2Generator = findVal({oneSums:Int, listSizeVal: Double -> if (oneSums.compareTo(listSizeVal) >= 0) 1 else 0})
    val o2Rate = o2Generator.joinToString("").toInt(2)
    val co2Scrubber = findVal({oneSums:Int, listSizeVal: Double -> if (oneSums.compareTo(listSizeVal) >= 0) 0 else 1})
    val co2Rate = co2Scrubber.joinToString("").toInt(2)
    val part2Ans = o2Rate * co2Rate
    println("part2Ans = $part2Ans")
    
}

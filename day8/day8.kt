
package org.kotlinlang.play
import kotlin.math.abs

import kotlin.collections.sum
import java.io.File


fun main() {
    val inputStrings = File("day8.txt").readLines()
    val lines: List<List<String>> = inputStrings.map { it.split(" | ") }
    var inputs = mutableListOf<List<String>>()
    var outputs = mutableListOf<List<String>>()
    for (line in lines) {
        inputs.add(line[0].split(" "))
        outputs.add(line[1].split(" "))
    }
    val digitLengths = mutableListOf(2, 3 ,4, 7)
    var foundDigits = 0
    for (output in outputs) {
        foundDigits += output.filter{ digitLengths.contains(it.length) }.size
    }
    println("Part 1 = $foundDigits")

    fun findCodes(lines: List<String>): HashMap<String, Int> {
        val codes = arrayOfNulls<String>(7)
        var one = mutableSetOf<Char>()
        var four = mutableSetOf<Char>()
        var seven = mutableSetOf<Char>()
        var eight = mutableSetOf<Char>()

        var rest = mutableListOf<Set<Char>>()
        var charOccur = mutableMapOf<Char, Int>()
        for (line in lines) {
            for (value in line.toCharArray()) {
                val keyValue = charOccur.get(value)
                if (keyValue != null) {
                    charOccur.put(value, keyValue + 1)
                } else {
                    charOccur.put(value, 1)
                }
            }
            var lineSet = line.toCharArray().toTypedArray().toSet()
            if (line.length == 2) {
                one.addAll(lineSet)
            } else if (line.length == 3) {
                seven.addAll(lineSet)
            } else if (line.length == 4) {
                four.addAll(lineSet)
            } else if (line.length == 4) {
                eight.addAll(lineSet)
            } else {
                rest.add(lineSet)
            }
        }

        val top = seven - one
        codes[0] = top.first().toString()

        // difference between length 6 and one will find top right
        val sixes = rest.filter{it.size == 6}
        for (six in sixes) {
            if ((one - six).size == 1) {
                codes[2] = (one - six).first().toString()
                break
            }
        }

        for (value in one) {
            if (value.toString() !in codes) {
                codes[5] = value.toString()
            }
        }
        
        for ((key, value) in charOccur) {
            if (value == 6) {
                codes[1] = key.toString()
            } else if (value == 4) {
                codes[4] = key.toString()
            }
        }

        // use 4 codes and codes[1] to find codes[3]
        val middle = four - one
        for (value in middle) {
            if (value.toString() !in codes) {
                codes[3] = value.toString()
            }
        }

        for (key in charOccur.keys) {
            if (key.toString() !in codes) {
                codes[6] = key.toString()
                break
            }
        }

        val values = hashMapOf(
            0 to listOf(0, 1, 2, 4, 5, 6),
            1 to listOf(2, 5),
            2 to listOf(0,2,3,4,6),
            3 to listOf(0,2,3,5,6),
            4 to listOf(1,2,3,5),
            5 to listOf(0,1,3,5,6),
            6 to listOf(0,1,3,4,5,6),
            7 to listOf(0,2,5),
            8 to listOf(0,1,2,3,4,5,6),
            9 to listOf(0,1,2,3,5,6)
        )
        
        val codeMap = hashMapOf<String, Int>()
        for ((number, indexes) in values) {
            val code = indexes.map { codes[it] }.joinToString("").toCharArray().sorted().joinToString("")
            codeMap.put(code, number)
        }
        return codeMap
    }



    val outputValues = mutableListOf<Int>()
    for (n in 0..inputs.size - 1) {
        val codeMap = findCodes(inputs[n])
        val digits = mutableListOf<Int>()
        for (value in outputs[n]) {
            val code = value.toCharArray().sorted().joinToString("")
            val number = codeMap.get(code)
            if (number != null){
                digits.add(number)
            }
        }
        outputValues.add(digits.joinToString("").toInt())
    }
    val part2Ans = outputValues.sum()
    println("Part 2 = $part2Ans")

    
}
package aoc.days.day8

import aoc.inputOfDay

typealias PatternEntry = Pair<List<String>, List<String>>

fun main() {
    val input = parseInput(inputOfDay(8))
    println(task1(input))
    println(task2(input))
}

fun parseInput(input: List<String>): List<PatternEntry> {
    return input.map {
        it.split(" | ")
            .let { (pattern, num) ->
                Pair(pattern.split(" "), num.split(" "))
            }
    }
}

fun task1(input: List<PatternEntry>): Int = input
    .sumOf { (_, number) ->
        number.sumOf {
            when (it.length) {
                2, 3, 4, 7 -> 1
                else -> 0
            } as Int
        }
    }

fun task2(input: List<PatternEntry>): Int {
    infix fun String.allIn(other: String) = this.all { it in other }
    fun List<String>.firstWithLength(length: Int): String = this.first { it.length == length }
    fun getNumber(number: String, pattern: List<String>): Char = when (number.length) {
        2 -> '1'
        4 -> '4'
        3 -> '7'
        7 -> '8'
        5 -> {
            val one by lazy { pattern.firstWithLength(2) }
            val six by lazy { pattern.filter { it.length == 6 }.first { getNumber(it, pattern) == '6' } }
            if (one allIn number) '3' else if (number allIn six) '5' else '2'
        }
        6 -> {
            val four by lazy { pattern.firstWithLength(4) }
            val one by lazy { pattern.firstWithLength(2) }
            if (four allIn number) '9' else if (one allIn number) '0' else '6'
        }
        else -> error("invalid pattern length")
    }

    return input.sumOf { (pattern, numbers) -> numbers.map { getNumber(it, pattern) }.joinToString("").toInt() }
}
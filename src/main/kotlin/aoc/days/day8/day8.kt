package aoc.days.day8

import aoc.inputOfDay

typealias PatternEntry = Pair<List<String>, List<String>>

fun main() {
    val input = parseInput(inputOfDay(8))
    println(task1(input))
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
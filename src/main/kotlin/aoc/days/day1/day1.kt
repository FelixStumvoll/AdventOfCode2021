package aoc.days.day1

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(1).map { it.toInt() }
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<Int>): Int = input.windowed(2).count { (a,b) -> a < b }

fun task2(input: List<Int>): Int = input.windowed(3).map { it.sum() }.let{ task1(it) }
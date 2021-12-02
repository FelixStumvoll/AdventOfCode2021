package aoc.tasks

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(1).map { it.toInt() }
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<Int>): Int =
    input.drop(1)
        .fold(Pair(input.first(), 0)) { (num, count), next ->
            Pair(next, if (next > num) count + 1 else count)
        }.second

fun task2(input: List<Int>): Int =
    input.windowed(3).map { it.sum() }.let{ task1(it) }
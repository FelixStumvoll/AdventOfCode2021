package aoc.tasks

import aoc.inputOfDay


typealias Command = Pair<String, Int>

fun main() {
    val input = inputOfDay(2)
        .map { l -> l.split(" ").let { Pair(it[0], it[1].toInt()) } }
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<Command>): Int =
    input.fold(Pair(0, 0)) { (h, d), (command, units) ->
        when (command) {
            "forward" -> Pair(h + units, d)
            "up" -> Pair(h, d - units)
            "down" -> Pair(h, d + units)
            else -> throw IllegalArgumentException()
        }
    }.let { it.first * it.second }

fun task2(input: List<Command>): Int =
    input.fold(Triple(0, 0, 0)) { (h, d, a), (command, units) ->
        when (command) {
            "forward" -> Triple(h + units, d + a * units, a)
            "up" -> Triple(h, d, a - units)
            "down" -> Triple(h, d, a + units)
            else -> throw IllegalArgumentException()
        }
    }.let { it.first * it.second }
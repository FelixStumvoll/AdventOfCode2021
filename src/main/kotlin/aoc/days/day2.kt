package aoc.days

import aoc.inputOfDay
import aoc.days.Direction.*

enum class Direction {
    Forward, Up, Down;

    companion object {
        fun of(value: String): Direction = when (value) {
            "forward" -> Forward
            "up" -> Up
            "down" -> Down
            else -> error("invalid direction")
        }
    }
}
typealias Command = Pair<Direction, Int>

fun main() {
    val input = inputOfDay(2)
        .map { l -> l.split(' ').let { Pair(Direction.of(it[0]), it[1].toInt()) } }
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<Command>): Int =
    input.fold(Pair(0, 0)) { (h, d), (direction, units) ->
        when (direction) {
            Forward -> Pair(h + units, d)
            Up -> Pair(h, d - units)
            Down -> Pair(h, d + units)
        }
    }.let { it.first * it.second }

fun task2(input: List<Command>): Int =
    input.fold(Triple(0, 0, 0)) { (h, d, a), (direction, units) ->
        when (direction) {
            Forward -> Triple(h + units, d + a * units, a)
            Up -> Triple(h, d, a - units)
            Down -> Triple(h, d, a + units)
        }
    }.let { it.first * it.second }
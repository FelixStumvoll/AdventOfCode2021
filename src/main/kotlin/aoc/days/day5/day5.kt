package aoc.days.day5

import aoc.inputOfDay

data class Point(val x: Int, val y: Int) {
    infix fun to(other: Point): List<Point> {
        val xRange = if (x >= other.x) x.downTo(other.x) else x..other.x
        val yRange = if (y >= other.y) y.downTo(other.y) else y..other.y

        return when {
            x == other.x -> yRange.map { Point(x, it) }
            y == other.y -> xRange.map { Point(it, y) }
            else -> xRange.zip(yRange).map { (x, y) -> Point(x, y) }
        }
    }
}

typealias VentLines = List<Pair<Point, Point>>

fun parseInput(input: List<String>): VentLines = input
    .map { line ->
        line.split(" -> ")
            .map { pair -> pair.split(",").let { (x, y) -> Point(x.toInt(), y.toInt()) } }
            .let { (start, end) -> Pair(start, end) }
    }

fun main() {
    val input = parseInput(inputOfDay(5))
    println(task1(input))
    println(task2(input))
}

fun buildVentMap(input: VentLines): Int =
    input.flatMap { (start, end) -> start to end }
        .groupBy { it.x }.mapValues { (_, v) -> v.groupingBy { it.y }.eachCount() }
        .map { it.value.filter { (_, v) -> v > 1 }.values.count() }.sum()

fun task1(input: VentLines): Int = buildVentMap(input.filter { (start, end) -> start.x == end.x || start.y == end.y })
fun task2(input: VentLines): Int = buildVentMap(input)

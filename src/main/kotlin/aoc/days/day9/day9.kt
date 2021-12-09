package aoc.days.day9

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(9).map { line -> line.map { it.digitToInt() } }
    println(task1(input))
    println(task2(input))
}

fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> =
    listOf(Pair(first - 1, second), Pair(first + 1, second), Pair(first, second - 1), Pair(first, second + 1))

fun <T> List<List<T>>.get(pos: Pair<Int, Int>): T? = this.getOrNull(pos.first)?.getOrNull(pos.second)

fun getLowPoints(input: List<List<Int>>): List<Pair<Int, Int>> {
    fun isMin(number: Int, current: Pair<Int, Int>): Boolean =
        current.neighbours()
            .mapNotNull { input.get(it) }
            .all { it > number }

    return input.flatMapIndexed { x, line ->
        line.mapIndexedNotNull { y, number -> Pair(x, y).takeIf { isMin(number, it) } }
    }
}

fun task1(input: List<List<Int>>) = getLowPoints(input).mapNotNull { input.get(it)?.plus(1) }.sum()

fun task2(input: List<List<Int>>): Int {
    fun findBasinRec(current: Pair<Int, Int>, basin: Set<Pair<Int, Int>>): Set<Pair<Int, Int>> {
        val nextToCheck = current.neighbours()
            .filter {
                input.get(it)?.let { num -> num > (input.get(current) ?: 9) } == true
                        && input.get(it)?.let { num -> num != 9 } == true
                        && it !in basin
            }

        val newBasin = basin + current
        return when (nextToCheck.size) {
            0 -> newBasin
            else -> newBasin + nextToCheck.flatMap { findBasinRec(it, newBasin) }
        }
    }

    return getLowPoints(input).asSequence()
        .map { findBasinRec(it, setOf()).size }
        .sortedDescending()
        .take(3)
        .fold(1) { acc, i -> acc * i }
}
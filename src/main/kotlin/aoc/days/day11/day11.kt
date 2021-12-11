package aoc.days.day11

import aoc.get
import aoc.inputOfDay

fun main() {
    val input = inputOfDay(11).map { line -> line.map { it.digitToInt() } }
    println(task1(input))
    println(task2(input))
}

fun neighbours(x: Int, y: Int): List<Pair<Int, Int>> =
    (x - 1..x + 1).flatMap { x1 -> (y - 1..y + 1).map { x1 to it } }
        .filterNot { it == Pair(x, y) }

fun MutableList<MutableList<Int>>.updateFlashes() {
    fun handleNeighbours(x: Int, y: Int, seen: MutableSet<Pair<Int, Int>>) {
        if (Pair(x, y) in seen) return
        seen.add(Pair(x, y))
        neighbours(x, y).filter { it !in seen }.forEach { pos ->
            get(pos)?.let {
                val (x1, y1) = pos
                this[x1][y1]++
                if (this[x1][y1] > 9) {
                    handleNeighbours(x1, y1, seen)
                }
            }
        }
    }

    val seen = mutableSetOf<Pair<Int, Int>>()
    for (x in 0..lastIndex) {
        for (y in 0..this[x].lastIndex) {
            this[x][y]++
            if (this[x][y] > 9) {
                handleNeighbours(x, y, seen)
            }
        }
    }
}

fun MutableList<MutableList<Int>>.resetFlashes() {
    for (x in 0..lastIndex) {
        for (y in 0..this[x].lastIndex) {
            if (this[x][y] > 9) {
                this[x][y] = 0
            }
        }
    }
}

fun task1(input: List<List<Int>>): Int {
    val current = input.map { it.toMutableList() }.toMutableList()
    var flashes = 0

    for (iter in 1..100) {
        current.updateFlashes()
        flashes += current.sumOf { line -> line.count { it > 9 } }
        current.resetFlashes()
    }

    return flashes
}

fun task2(input: List<List<Int>>): Int {
    fun step(current: MutableList<MutableList<Int>>, currIteration: Int): Int {
        current.updateFlashes()
        return when (current.all { line -> line.all { it > 9 } }) {
            true -> currIteration
            false -> {
                current.resetFlashes()
                step(current, currIteration + 1)
            }
        }
    }

    return step(input.map { it.toMutableList() }.toMutableList(), 1)
}
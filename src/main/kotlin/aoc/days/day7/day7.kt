package aoc.days.day7

import aoc.inputOfDay
import kotlin.math.abs

fun main() {
    val input = inputOfDay(7).first().split(",").map { it.toInt() }
    println(task1(input))
    println(task2(input))
}

fun List<Int>.minToMax() = (this.minOf { it }..this.maxOf { it })

fun task1(input: List<Int>): Int = input.minToMax().map { i -> input.sumOf { abs(it - i) } }.minOf { it }

fun task2(input: List<Int>): Int = input.minToMax()
    .map { i -> input.sumOf { (1..abs(it - i)).sum() } }
    .minOf { it }
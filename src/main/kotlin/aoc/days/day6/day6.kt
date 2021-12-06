package aoc.days.day6

import aoc.inputOfDay
import java.math.BigInteger

fun main() {
    val input = inputOfDay(6).first().split(",").map { it.toInt() }
    println(task1(input))
    println(task2(input))
}

fun simulateFishPopulation(days: Int, initialPopulation: List<Int>): BigInteger {
    tailrec fun simulate(remainingDays: Int, population: Map<Int, BigInteger>): BigInteger = when (remainingDays) {
        0 -> population.values.sumOf { it }
        else -> {
            fun MutableMap<Int, BigInteger>.plusOrInsert(key: Int, value: BigInteger) =
                compute(key) { _, v -> v?.plus(value) ?: value }

            val updatedDays = population.mapKeys { (key, _) -> key - 1 }.toMutableMap().apply {
                remove(-1)?.let {
                    plusOrInsert(6, it)
                    plusOrInsert(8, it)
                }
            }
            simulate(remainingDays - 1, updatedDays)
        }
    }

    return simulate(days, initialPopulation.groupingBy { it }.eachCount().mapValues { (_, v) -> v.toBigInteger() })
}

fun task1(input: List<Int>) = simulateFishPopulation(80, input)
fun task2(input: List<Int>) = simulateFishPopulation(256, input)
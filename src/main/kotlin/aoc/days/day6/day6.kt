package aoc.days.day6

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(6).first().split(",").map { it.toInt() }
    println(task1(input))
    println(task2(input))
}

fun simulateFishPopulation(days: Int, initialPopulation: List<Int>): Long {
    fun simulate(remainingDays: Int, population: Map<Int, Long>): Long = when (remainingDays) {
        0 -> population.values.sumOf { it }
        else -> {
            fun Map<Int, Long>.plusOrInsert(key: Int, value: Long) =
                this + Pair(key, get(key)?.plus(value) ?: value)

            val updatedDays = population.mapKeys { (key, _) -> key - 1 }.let { map ->
                map[-1]?.let { map.plusOrInsert(6, it).plusOrInsert(8, it).minus(-1) } ?: map
            }
            simulate(remainingDays - 1, updatedDays)
        }
    }

    return simulate(days, initialPopulation.groupingBy { it }.eachCount().mapValues { (_, v) -> v.toLong() })
}

fun task1(input: List<Int>) = simulateFishPopulation(80, input)
fun task2(input: List<Int>) = simulateFishPopulation(256, input)
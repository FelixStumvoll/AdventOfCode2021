package aoc.days

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(3)
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<String>): Int {
    tailrec fun findMostCommonBits(
        input: List<String>,
        index: Int,
        currentBinary: String,
        appendOne: (oneCount: Int) -> Boolean
    ): String = when {
        index >= input[0].length -> currentBinary
        else -> {
            val oneCount = input.count { it[index] == '1' }
            val updatedString = currentBinary + (if (appendOne(oneCount)) '1' else '0')
            findMostCommonBits(input, index + 1, updatedString, appendOne)
        }
    }

    val elementsHalf = input.size / 2
    val gamma = findMostCommonBits(input, 0, "") { it > elementsHalf }
    val epsilon = findMostCommonBits(input, 0, "") { it < elementsHalf }
    return gamma.toInt(2) * epsilon.toInt(2)
}

fun task2(input: List<String>): Int {
    tailrec fun findSingleRecursive(
        input: List<String>,
        index: Int,
        chooseLarger: Boolean,
        tieBreaker: (ones: List<String>, zeros: List<String>) -> List<String>
    ): String {
        val (ones, zeros) = input.partition { it[index] == '1' }
        val filtered = when {
            ones.size == zeros.size -> tieBreaker(ones, zeros)
            ones.size > zeros.size && chooseLarger -> ones
            ones.size < zeros.size && !chooseLarger -> ones
            else -> zeros
        }
        return filtered.singleOrNull() ?: findSingleRecursive(filtered, index + 1, chooseLarger, tieBreaker)
    }

    val o2 = findSingleRecursive(input, 0, true) { ones, _ -> ones }
    val co2 = findSingleRecursive(input, 0, false) { _, zeros -> zeros }
    return o2.toInt(2) * co2.toInt(2)
}
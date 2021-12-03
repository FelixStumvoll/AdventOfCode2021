package aoc.tasks

import aoc.inputOfDay
import aoc.tasks.Bit.One
import aoc.tasks.Bit.Zero
import kotlin.math.pow

enum class Bit {
    Zero, One;

    fun toInt(bitPosition: Int) = when (this) {
        Zero -> 0
        One -> (2.toDouble().pow(bitPosition - 1).toInt())
    }

    companion object {
        fun of(char: Char): Bit = when (char) {
            '0' -> Zero
            '1' -> One
            else -> error("invalid bit")
        }
    }
}
typealias BinaryNum = List<Bit>

fun BinaryNum.toInt() = this.mapIndexed { i, bit -> bit.toInt(i + (this.size - i * 2)) }.sum()

fun main() {
    val input = inputOfDay(3).map { bin -> bin.map { Bit.of(it) } }
    println(task1(input))
    println(task2(input))
}

fun task1(input: List<BinaryNum>): Int {
    val bits = input[0].size

    tailrec fun task1Recursively(input: List<BinaryNum>, index: Int, current: Pair<Int, Int>): Pair<Int, Int> {
        if (index >= input[0].size) return current

        val oneCount = input.count { it[index] == One }
        val decimal = One.toInt(index + (bits - index * 2))
        val updated =
            if (oneCount > (input.size / 2)) Pair(current.first + decimal, current.second)
            else Pair(current.first, current.second + decimal)

        return task1Recursively(input, index, updated)
    }

    return task1Recursively(input, 0, Pair(0, 0)).let { it.first * it.second }
}

fun task2(input: List<BinaryNum>): Int {
    tailrec fun findSingleRecursive(
        input: List<BinaryNum>,
        index: Int,
        equalityBreaker: Bit,
        chooseLarger: Boolean
    ): BinaryNum {
        val split = input.partition { it[index] == One }
        val filtered = when {
            split.first.size == split.second.size ->
                when (equalityBreaker) {
                    One -> split.first
                    Zero -> split.second
                }
            split.first.size > split.second.size && chooseLarger -> split.first
            split.first.size < split.second.size && !chooseLarger -> split.first
            else -> split.second
        }
        return filtered.singleOrNull() ?: findSingleRecursive(filtered, index + 1, equalityBreaker, chooseLarger)
    }

    val ogr = findSingleRecursive(input, 0, One, true)
    val ocr = findSingleRecursive(input, 0, Zero, false)

    return ogr.toInt() * ocr.toInt()
}
package aoc.days.day10

import aoc.inputOfDay

fun main() {
    val input = inputOfDay(10).map { it.toList() }
    println(task1(input))
    println(task2(input))
}

fun Char.isOpening(): Boolean = when (this) {
    '[', '(', '{', '<' -> true
    else -> false
}

infix fun Char?.isClosingOf(char: Char?): Boolean = this == char?.getClosing()

fun Char.getClosing(): Char = when (this) {
    '[' -> ']'
    '(' -> ')'
    '{' -> '}'
    '<' -> '>'
    else -> error("invalid bracket")
}

fun lineCorruption(input: List<Char>): Int {
    fun Char.getPoints(): Int = when (this) {
        ']' -> 57
        ')' -> 3
        '}' -> 1197
        '>' -> 25137
        else -> error("invalid bracket")
    }

    tailrec fun checkLine(currentLine: List<Char>, index: Int, stack: List<Char>): Int {
        val currentChar = currentLine.getOrNull(index)
        val stackHead = stack.lastOrNull()

        return when {
            index > currentLine.lastIndex -> 0
            currentChar?.isOpening() == true -> checkLine(currentLine, index + 1, stack + currentChar)
            currentChar.isClosingOf(stackHead) -> checkLine(currentLine, index + 1, stack.dropLast(1))
            else -> currentChar?.getPoints() ?: 0
        }
    }

    return checkLine(input, 0, listOf())
}

fun task1(input: List<List<Char>>): Int = input.sumOf { lineCorruption(it) }

fun task2(input: List<List<Char>>): Long {
    fun <T> List<T>.middle() = this[size / 2]

    fun Char.getPoints(): Long = when (this) {
        ']' -> 2
        ')' -> 1
        '}' -> 3
        '>' -> 4
        else -> error("invalid bracket")
    }

    fun repairLine(currentLine: List<Char>, index: Int, stack: List<Char>): List<Char> {
        val currentChar = currentLine.getOrNull(index)
        val stackHead = stack.lastOrNull()

        return when {
            index > currentLine.lastIndex ->
                when (stackHead) {
                    null -> listOf()
                    else -> repairLine(currentLine, index, stack.dropLast(1)) + stackHead.getClosing()
                }
            currentChar?.isOpening() == true -> repairLine(currentLine, index + 1, stack + currentChar)
            currentChar isClosingOf stackHead -> repairLine(currentLine, index + 1, stack.dropLast(1))
            else -> listOf()
        }
    }

    return input.filter { lineCorruption(it) == 0 }
        .map { repairLine(it, 0, listOf()).fold(0L) { acc, char -> (acc * 5) + char.getPoints() } }
        .sorted()
        .middle()
}
package aoc.days

import aoc.inputOfDay

data class BingoCell(val number: Int, val marked: Boolean = false)

data class BingoField(val field: List<List<BingoCell>>) : List<List<BingoCell>> by field {
    fun update(number: Int): BingoField =
        BingoField(map { row -> row.map { cell -> if (cell.number == number) BingoCell(number, true) else cell } })

    fun isBingo(): Boolean = any { row -> row.all { it.marked } }
            || (0..field[0].lastIndex).any { index -> map { row -> row[index] }.all { it.marked } }

    fun unmarkedSum(): Int = flatMap { row -> row.filterNot { it.marked }.map { it.number } }.sum()
}

fun parseInput(input: List<String>): Pair<List<Int>, List<BingoField>> {
    val numbers = input.first().split(",").map { it.toInt() }
    val bingoField = input.drop(2).filterNot { it.isEmpty() }.chunked(5)
        .map { rows ->
            BingoField(rows.map { line ->
                line.split(" ").filterNot { it.isEmpty() }.map { BingoCell(it.toInt()) }
            })
        }

    return Pair(numbers, bingoField)
}

fun main() {
    val (numbers, bingoFields) = parseInput(inputOfDay(4))
    println(task1(numbers, bingoFields))
    println(task2(numbers, bingoFields))
}

fun task1(numbers: List<Int>, bingoFields: List<BingoField>): Int {
    tailrec fun findFirstWinningBoard(numberIndex: Int, fields: List<BingoField>): Int {
        val currentNumber = numbers[numberIndex]
        val updatedFields = fields.map { it.update(currentNumber) }
        return updatedFields.firstOrNull { it.isBingo() }?.unmarkedSum()?.times(currentNumber)
            ?: findFirstWinningBoard(numberIndex + 1, updatedFields)
    }

    return findFirstWinningBoard(0, bingoFields)
}

fun task2(numbers: List<Int>, bingoFields: List<BingoField>): Int {
    tailrec fun findLastWinningBoard(numberIndex: Int, fields: List<BingoField>): Int {
        val currentNumber = numbers[numberIndex]
        val updatedFields = fields.map { it.update(currentNumber) }

        return when {
            updatedFields.singleOrNull()?.isBingo() == true -> updatedFields[0].unmarkedSum() * currentNumber
            else -> findLastWinningBoard(numberIndex + 1, updatedFields.filterNot { it.isBingo() })
        }
    }

    return findLastWinningBoard(0, bingoFields)
}
package aoc

fun inputOfDay(day: Int): List<String> = Unit.javaClass.getResource("/inputs/day$day.txt").readText().lines()

fun <T> List<List<T>>.get(pos: Pair<Int, Int>): T? = this.getOrNull(pos.first)?.getOrNull(pos.second)
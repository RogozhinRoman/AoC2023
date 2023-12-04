import kotlin.math.pow

fun main() {
    val lines = readInput("Day04")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    return lines.asSequence().map { it.split(": ").last() }
        .map { it -> it.split('|').map { it.trim() } }
        .map { Pair(getNumbersSet(it.first()), getNumbersSet(it.last())) }
        .map { it.first.intersect(it.second).size }
        .sumOf {
            if (it > 0) 2.toDouble().pow((it - 1).toDouble()) else 0.toDouble()
        }
        .toInt()
}

private fun getNumbersSet(it: String) = it.split(Regex("\\s+")).map { it.toInt() }.toSet()

private fun part2(lines: List<String>): Int {
    val cardCopies = IntArray(lines.size) {1}
    val winningCards = lines.asSequence().map { it.split(": ").last() }
        .map { it -> it.split('|').map { it.trim() } }
        .map { Pair(getNumbersSet(it.first()), getNumbersSet(it.last())) }
        .map { it.first.intersect(it.second).size }
        .toList()

    for ((i, number) in winningCards.withIndex()) {
        for (j in 1..number) {
            cardCopies[i + j] += cardCopies[i]
        }
    }

    return cardCopies.sum()
}

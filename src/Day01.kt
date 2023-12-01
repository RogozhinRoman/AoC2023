fun main() {
    val lines = readInput("Day01")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    return lines.sumOf { it -> (it.first { it.isDigit() }.toString() + it.last { it.isDigit() }.toString()).toInt() }
}

private fun part2(lines: List<String>): Int {
    val numbersMap = mapOf(
        "one" to "1",
        "two" to "2",
        "three" to "3",
        "four" to "4",
        "five" to "5",
        "six" to "6",
        "seven" to "7",
        "eight" to "8",
        "nine" to "9"
    )

    var total = 0
    for (line in lines) {
        var spelledNumbers = numbersMap.keys
            .map { Pair(it, line.indexOf(it)) }
            .filter { it.second != -1 }

        var firstDigit = line.firstOrNull { it.isDigit() }.toString()
        var firstSpelledNumber = Pair("", Int.MAX_VALUE)
        if (spelledNumbers.isNotEmpty()) {
            firstSpelledNumber = spelledNumbers.minBy { it.second }
        }

        firstDigit =
            if (line.indexOf(firstDigit) < firstSpelledNumber.second) firstDigit else numbersMap[firstSpelledNumber.first]!!

        spelledNumbers = numbersMap.keys
            .map { Pair(it, line.lastIndexOf(it)) }
            .filter { it.second != -1 }

        var lastDigit = line.lastOrNull { it.isDigit() }.toString()
        var lastSpelledNumber = Pair("", Int.MIN_VALUE)
        if (spelledNumbers.isNotEmpty()) {
            lastSpelledNumber = spelledNumbers.maxBy { it.second }
        }

        lastDigit =
            if (line.lastIndexOf(lastDigit) < lastSpelledNumber.second) numbersMap[lastSpelledNumber.first]!! else lastDigit

        total += (firstDigit + lastDigit).toInt()
    }

    return total
}

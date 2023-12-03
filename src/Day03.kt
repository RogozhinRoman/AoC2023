fun main() {
    val lines = readInput("Day03")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val engineParts = mutableListOf<Int>()

    for ((i, line) in lines.withIndex()) {
        var currentNumber = ""
        var hasNeighbourSymbol = false
        for ((j, symb) in line.withIndex()) {
            if (symb.isDigit()) {
                hasNeighbourSymbol = hasNeighbourSymbol || hasNeighbourSymbol(i, j, lines)
                currentNumber += symb
            }

            if (!symb.isDigit() || j == line.length - 1) {
                if (hasNeighbourSymbol) {
                    engineParts.add(currentNumber.toInt())
                }

                currentNumber = ""
                hasNeighbourSymbol = false
            }
        }
    }

    return engineParts.sum()
}

fun hasNeighbourSymbol(i: Int, j: Int, lines: List<String>): Boolean {
    val diff = arrayOf(-1, 0, 1)
    for (dx in diff) {
        for (dy in diff) {
            if (dx == 0 && dy == 0) {
                continue
            }
            if (i + dx < 0 || i + dx >= lines.size || j + dy < 0 || j + dy >= lines[i].length) {
                continue
            }

            val neighbour = lines[i + dx][j + dy]
            if (neighbour != '.' && !neighbour.isDigit()) {
                return true
            }
        }
    }

    return false
}

private fun part2(lines: List<String>): Int {
    return 0
}

fun main() {
    val lines = readInput("Day14")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val map = lines.map { it.toCharArray() }
    tiltNorth(map)
    var currentMultiplier = map.size
    return map.fold(0) { acc, chars ->
        acc + chars.count { it == 'O' } * currentMultiplier.also { currentMultiplier-- }
    }
}

private fun tiltNorth(map: List<CharArray>) {
    for ((i, line) in map.withIndex()) {
        for ((j, symb) in line.withIndex()) {
            if (symb != 'O') continue

            var currentRow = i
            while (currentRow > 0) {
                if (map[currentRow - 1][j] == '.') {
                    map[currentRow - 1][j] = 'O'
                    map[currentRow][j] = '.'
                    currentRow--
                } else break
            }
        }
    }
}

private fun tiltSouth(map: List<CharArray>) {
    for (i in map.lastIndex downTo 0) {
        for ((j, symb) in map[i].withIndex()) {
            if (symb != 'O') continue

            var currentRow = i
            while (currentRow < map.lastIndex) {
                if (map[currentRow + 1][j] == '.') {
                    map[currentRow + 1][j] = 'O'
                    map[currentRow][j] = '.'
                    currentRow++
                } else break
            }
        }
    }
}

private fun tiltWest(map: List<CharArray>) {
    for ((i, line) in map.withIndex()) {
        for (j in line.lastIndex downTo 0) {
            if (line[j] != 'O') continue

            var currentColumn = j
            while (currentColumn < line.lastIndex) {
                if (map[i][currentColumn + 1] == '.') {
                    map[i][currentColumn + 1] = 'O'
                    map[i][currentColumn] = '.'
                    currentColumn++
                } else break
            }
        }
    }
}

private fun tiltEast(map: List<CharArray>) {
    for ((i, line) in map.withIndex()) {
        for ((j, symb) in line.withIndex()) {
            if (symb != 'O') continue

            var currentColumn = j
            while (currentColumn > 0) {
                if (map[i][currentColumn - 1] == '.') {
                    map[i][currentColumn - 1] = 'O'
                    map[i][currentColumn] = '.'
                    currentColumn--
                } else break
            }
        }
    }
}

private fun part2(lines: List<String>): Int {
    val map = lines.map { it.toCharArray() }

    val cache = mutableMapOf<String, Int>()
    cache[mapToString(map)] = 0
    val N = 1000000000
    for (i in 0..N) {
        tiltNorth(map)
        tiltEast(map)
        tiltSouth(map)
        tiltWest(map)

        val newKey = mapToString(map)
        if (cache.containsKey(newKey)) {
            val cycleLength = i - cache[newKey]!!
            val remainingIterations = (N - i) % cycleLength
            for (j in 0 until remainingIterations) {
                tiltNorth(map)
                tiltEast(map)
                tiltSouth(map)
                tiltWest(map)
            }
            break
        } else cache[newKey] = i
    }

    var currentMultiplier = map.size
    return map.fold(0) { acc, chars ->
        acc + chars.count { it == 'O' } * currentMultiplier.also { currentMultiplier-- }
    }
}

private fun mapToString(map: List<CharArray>) = map.joinToString("\n") { it.joinToString("") }

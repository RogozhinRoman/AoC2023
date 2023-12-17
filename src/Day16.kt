fun main() {
    val lines = readInput("Day16")

    println(part1(lines))
    println(part2(lines))
}

private class Beam(
    var x: Int,
    var y: Int,
    var direction: Direction
)

private enum class Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT
}

private fun getNewDirection(currentDirection: Direction, mirror: String): Direction {
    return when {
        currentDirection == Direction.UP && mirror == "/" -> Direction.RIGHT
        currentDirection == Direction.UP && mirror == "\\" -> Direction.LEFT
        currentDirection == Direction.DOWN && mirror == "\\" -> Direction.RIGHT
        currentDirection == Direction.DOWN && mirror == "/" -> Direction.LEFT
        currentDirection == Direction.RIGHT && mirror == "/" -> Direction.UP
        currentDirection == Direction.RIGHT && mirror == "\\" -> Direction.DOWN
        currentDirection == Direction.LEFT && mirror == "\\" -> Direction.UP
        currentDirection == Direction.LEFT && mirror == "/" -> Direction.DOWN
        else -> throw Exception()
    }
}

private fun part1(lines: List<String>): Int {
    val grid = lines.map { it.toCharArray() }.toTypedArray()
    val queue = ArrayDeque<Beam>()
    queue.addFirst(Beam(0, -1, Direction.RIGHT))

    return getEnergizedTilesAmount(queue, grid, lines)
}

private fun getEnergizedTilesAmount(
    queue: ArrayDeque<Beam>,
    grid: Array<CharArray>,
    lines: List<String>
): Int {
    val visitedTiles = mutableSetOf<Triple<Int, Int, Direction>>() // x, y, dir

    while (queue.isNotEmpty()) {
        val beam = queue.removeFirst()
        val current = Triple(beam.x, beam.y, beam.direction)
        if (current in visitedTiles) continue
        visitedTiles.add(current)

        when (beam.direction) {
            Direction.UP -> beam.x--
            Direction.DOWN -> beam.x++
            Direction.RIGHT -> beam.y++
            Direction.LEFT -> beam.y--
        }

        if (beam.x < 0 || beam.y < 0 || beam.x >= grid.size || beam.y >= grid.size) {
            continue
        } else if (lines[beam.x][beam.y] == '.') {
            queue.addLast(beam)
            continue
        } else if (lines[beam.x][beam.y] == '/' || lines[beam.x][beam.y] == '\\') {
            beam.direction = getNewDirection(beam.direction, lines[beam.x][beam.y].toString())
            queue.addLast(beam)
        } else if (lines[beam.x][beam.y] == '|' || lines[beam.x][beam.y] == '-') {
            val lens = lines[beam.x][beam.y].toString()
            if ((beam.direction == Direction.UP || beam.direction == Direction.DOWN) && lens == "|") {
                queue.addLast(beam)
            } else if ((beam.direction == Direction.RIGHT || beam.direction == Direction.LEFT) && lens == "-") {
                queue.addLast(beam)
            } else if ((beam.direction == Direction.UP || beam.direction == Direction.DOWN) && lens == "-") {
                beam.direction = Direction.LEFT
                queue.addLast(beam)
                queue.addLast(Beam(beam.x, beam.y, Direction.RIGHT))
            } else {
                beam.direction = Direction.UP
                queue.addLast(beam)
                queue.addLast(Beam(beam.x, beam.y, Direction.DOWN))
            }
        }
    }

    return visitedTiles.map { Pair(it.first, it.second) }.toSet().size - 1
}

private fun printMap(
    grid: Array<CharArray>,
    visitedTiles: MutableSet<Pair<Int, Int>>
) {
    for (i in 0..grid.size) {
        for (j in 0..grid.size) {
            if (Pair(i, j) !in visitedTiles) {
                print(".")
            } else {
                print("#")
            }
        }
        println()
    }
}

private fun part2(lines: List<String>): Int {
    val grid = lines.map { it.toCharArray() }.toTypedArray()


    var max = 0
    for (i in 0 until grid.size) {
        val queue = ArrayDeque<Beam>()
        queue.addFirst(Beam(i, -1, Direction.RIGHT))
        max = maxOf(max, getEnergizedTilesAmount(queue, grid, lines))
    }
    for (i in 0 until grid.size) {
        val queue = ArrayDeque<Beam>()
        queue.addFirst(Beam(i, grid.size, Direction.LEFT))
        max = maxOf(max, getEnergizedTilesAmount(queue, grid, lines))
    }
    for (i in 0 until grid.size) {
        val queue = ArrayDeque<Beam>()
        queue.addFirst(Beam(-1, i, Direction.DOWN))
        max = maxOf(max, getEnergizedTilesAmount(queue, grid, lines))
    }
    for (i in 0 until grid.size) {
        val queue = ArrayDeque<Beam>()
        queue.addFirst(Beam(grid.size, i, Direction.UP))
        max = maxOf(max, getEnergizedTilesAmount(queue, grid, lines))
    }

    return max
}
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

private fun changeDirection(currentDirection: Direction, mirror: String): Direction {
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
    return 0
}

private fun part2(lines: List<String>): Int {
    return 0
}
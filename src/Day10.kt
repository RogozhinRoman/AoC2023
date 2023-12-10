fun main() {
    val lines = readInput("Day10")

    println(part1(lines))
    println(part2(lines))
}

val possibleMoves = listOf(Pair(0, -1), Pair(-1, 0), Pair(0, 1), Pair(1, 0))
val pipesMap = mapOf(
    "|" to listOf(Pair(-1, 0), Pair(1, 0)),
    "-" to listOf(Pair(0, -1), Pair(0, 1)),
    "L" to listOf(Pair(0, 1), Pair(-1, 0)),
    "J" to listOf(Pair(-1, 0), Pair(0, -1)),
    "7" to listOf(Pair(1, 0), Pair(0, -1)),
    "F" to listOf(Pair(1, 0), Pair(0, 1)),
    "S" to possibleMoves,
)

private fun part1(lines: List<String>): Int {
    val maze = lines.map { it -> it.map { it.toString() }.toMutableList() }

    val loop = getLoop(lines.map { it -> it.map { it.toString() }.toMutableList() }, getStartingPosition(maze))
    val start1 = loop[1]
    val start2 = loop[2]
    val reversed = loop.drop(1).reversed()
    val distances1 = loop.associateWith { 0 }.toMutableMap()
    val distances2 = loop.associateWith { 0 }.toMutableMap()
    val set = loop.toSet()

    for ((i, line) in maze.withIndex()) {
        for ((j, _) in line.withIndex()) {
            if (set.contains(Pair(i, j))) continue

            maze[i][j] = "."
        }
    }

    getMaxDistance(start1, reversed.toMutableSet(), distances1, maze)
    getMaxDistance(start2, reversed.toMutableSet(), distances2, maze)

    val map = distances1.filter { distances2[it.component1()] == it.component2() }.map { it.component2() }.filter { it != 0 }
    return map.first() + 1
}

private fun getMaxDistance(
    start1: Pair<Int, Int>,
    nodes: MutableSet<Pair<Int, Int>>,
    distances: MutableMap<Pair<Int, Int>, Int>,
    maze: List<MutableList<String>>
): Int {
    var stepsAmount = 0
    var currentNode = start1

    while (nodes.isNotEmpty()) {
        if (nodes.size == 1 && nodes.contains(currentNode)) break
        val nextNode = possibleMoves.map { Pair(it.first + currentNode.first, it.second + currentNode.second) }
            .first { nodes.contains(it) && pipesMap[maze[it.first][it.second]]!!.any { it1 -> isPossibleShift(it, it1, currentNode) } }

        stepsAmount++
        if (distances[nextNode]!! < stepsAmount) {
            distances[nextNode] = stepsAmount
        } else {
            return stepsAmount + 1
        }

        nodes.remove(currentNode)
        currentNode = nextNode
    }

    return stepsAmount
}

private fun printLoop(maze: List<MutableList<String>>) {
    for ((index, strings) in maze.withIndex()) {
        for ((index1, s) in strings.withIndex()) {
            print(maze[index][index1])
        }

        println()
    }

    println()
    println()
    println()
}

fun getLoop(maze: List<MutableList<String>>, current: Pair<Int, Int>): MutableList<Pair<Int, Int>> {
    val queue = ArrayDeque<Pair<Int, Int>>()
    queue.addLast(current)
    val loop = mutableListOf<Pair<Int, Int>>()
    loop.add(current)

    while (!queue.isEmpty()) {
        val head = queue.removeFirst()

        if (maze[head.first][head.second] == ".") continue
        val possibleNeighbours = pipesMap[maze[head.first][head.second]]!!.filter { isValidShift(it, head, maze) }
        maze[head.first][head.second] = "."

        for (neighbour in possibleNeighbours) {
            val neighbourPosition = Pair(head.first + neighbour.first, head.second + neighbour.second)
            val step = maze[neighbourPosition.first][neighbourPosition.second]
            if (!pipesMap.containsKey(step)) continue

            if (pipesMap[step]!!.any { isPossibleShift(it, neighbourPosition, head) }) {
                if (!loop.contains(neighbourPosition)) {
                    loop.add(neighbourPosition)
                }
                queue.addLast(neighbourPosition)
            }
        }
    }

    return loop
}

private fun isPossibleShift(
    it: Pair<Int, Int>,
    neighbourPosition: Pair<Int, Int>,
    head: Pair<Int, Int>
) = Pair(
    it.first + neighbourPosition.first,
    it.second + neighbourPosition.second
) == head

private fun isValidShift(
    it: Pair<Int, Int>,
    head: Pair<Int, Int>,
    maze: List<List<String>>
) = (it.first + head.first >= 0
        && it.first + head.first < maze.size
        && it.second + head.second >= 0
        && it.second + head.second < maze.first().size)

private fun getStartingPosition(maze: List<List<String>>): Pair<Int, Int> {
    for ((i, lines) in maze.withIndex()) {
        for ((j, s) in lines.withIndex()) {
            if (s == "S") return Pair(i, j)
        }
    }

    return Pair(-1, -1)
}

private fun part2(lines: List<String>): Int {
    return 0
}


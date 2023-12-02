import kotlin.math.max

fun main() {
    val lines = readInput("Day02")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val cubeLimits = mapOf(
        "red" to 12,
        "green" to 13,
        "blue" to 14,
    )

    var amountOfPossibleGames = 0
    for ((i, line) in lines.withIndex()) {
        val isPossibleGame = line.drop(line.indexOf(':') + 1)
            .split(';')
            .map { game -> game.split(',') }
            .all { gameCubes -> gameCubes.all { cube -> hasEnoughCubes(cube, cubeLimits) } }
        if (isPossibleGame) {
            amountOfPossibleGames += i + 1
        }
    }

    return amountOfPossibleGames
}

private fun hasEnoughCubes(cube: String, cubeLimits: Map<String, Int>): Boolean {
    val cubesDescription = cube.trim().split(' ')
    return cubeLimits[cubesDescription[1]]!! >= cubesDescription[0].toInt()
}

private fun part2(lines: List<String>): Int {
    val amountOfCubes = mutableMapOf(
        "red" to 0,
        "green" to 0,
        "blue" to 0,
    )

    var overallPower = 0
    for (line in lines) {
        line.drop(line.indexOf(':') + 1)
            .split(';')
            .flatMap { game -> game.split(',') }
            .map { cubes -> getCubeDescription(cubes) }
            .forEach { cubeDescription ->
                amountOfCubes[cubeDescription.second] =
                    max(amountOfCubes[cubeDescription.second]!!, cubeDescription.first)
            }

        overallPower += amountOfCubes.values.reduce { acc, i -> acc * i }
        amountOfCubes.replaceAll { _, _ -> 0 }
    }

    return overallPower
}

private fun getCubeDescription(cubes: String): Pair<Int, String> {
    val cubeDescription = cubes.trim().split(' ')
    return Pair(cubeDescription[0].toInt(), cubeDescription[1])
}

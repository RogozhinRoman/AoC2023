import java.math.BigDecimal

fun main() {
    val lines = readInput("Day08")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val instructions = lines.first()
    val instructionsMap = lines.drop(2)
        .map { it.split(" = ") }
        .associate { it[0] to getInstructions(it[1]) }

    val finishPosition = "ZZZ"
    var currentPosition = "AAA"
    var stepNumber = 0
    while (currentPosition != finishPosition) {
        val (left, right) = instructionsMap[currentPosition]!!

        currentPosition = if (instructions[stepNumber % instructions.length] == 'L') left else right

        stepNumber++
    }

    return stepNumber
}

private fun getInstructions(it: String): Pair<String, String> {
    val split = it.removePrefix("(")
        .removeSuffix(")")
        .split(", ")
    return Pair(split[0], split[1])
}

private fun part2(lines: List<String>): BigDecimal {
    val steps = lines.first()
    val instructionsMap = lines.drop(2)
        .map { it.split(" = ") }
        .associate { it[0] to getInstructions(it[1]) }

    return instructionsMap.keys.filter { it.endsWith("A") }
        .map { getIterationsNumber(it, instructionsMap, steps).toBigDecimal() }
        .reduce { acc, next -> lcm(acc, next) }
}

fun gcd(a: BigDecimal, b: BigDecimal): BigDecimal {
    if (b == BigDecimal.ZERO) return a
    return gcd(b, a % b)
}

fun lcm(a: BigDecimal, b: BigDecimal): BigDecimal {
    return a / gcd(a, b) * b
}

private fun getIterationsNumber(
    initialPosition: String,
    instructionsMap: Map<String, Pair<String, String>>,
    instructions: String
): Int {
    var stepNumber = 0
    var currentPosition = initialPosition
    while (!currentPosition.endsWith("Z")) {
        val (left, right) = instructionsMap[currentPosition]!!

        currentPosition = if (instructions[stepNumber % instructions.length] == 'L') left else right
        stepNumber++
    }

    return stepNumber
}

import java.math.BigDecimal

fun main() {
    val lines = readInput("Day01")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val racePairs = getRacePairs()

    var result = 1
    for (racePair in racePairs) {
        val (time, distanceToBeat) = racePair
        var currentRecords = 0

        for (i in 1..time) {
            val currentDistance = (time - i) * i
            if (currentDistance > distanceToBeat) {
                currentRecords++
            }
        }

        result *= currentRecords
    }

    return result
}

private fun part2(lines: List<String>): Int {
    //val (time, distanceToBeat) = Pair(71530, 940200)
    val (time, distanceToBeat) = Pair(41968894, BigDecimal(214178911271055))
    var currentRecords = 0

    for (i in 1..time) {
        val currentDistance = BigDecimal(time - i) * BigDecimal(i)
        if (currentDistance > distanceToBeat) {
            currentRecords++
        }
    }

    return currentRecords
}

private fun getRacePairs(): List<Pair<Int, Int>> {
//    return listOf(
//        Pair(7, 9),
//        Pair(15, 40),
//        Pair(30, 200),
//    )

    return listOf(
        Pair(41, 214),
        Pair(96, 1789),
        Pair(88, 1127),
        Pair(94, 1055),
    )
}



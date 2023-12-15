import java.util.LinkedList

fun main() {
    val lines = readInput("Day15")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val steps = lines.first().split(",")

    var totalSum = 0
    for (step in steps) {
        var currentVal = 0
        for (char in step) {
            currentVal += char.code
            currentVal *= 17
            currentVal %= 256
        }

        totalSum += currentVal
    }

    return totalSum
}

// "-" - remove all lens from the box and move remaining lenses without changing their order
// "=" :
//     - if there is already a lens with the same focal length in the box, remove it
//     - otherwise, add a lens with the given focal length to the box
private fun part2(lines: List<String>): Int {
    val boxes = MutableList(256) { LinkedList<CharSequence>() }
    val steps = lines.first().split(",")

    for (step in steps) {
        val boxNumber = getBoxNumber(step)
        val box = boxes[boxNumber]
        val command = step.dropWhile { it.isLetter() }.first()
        val label = step.takeWhile { it.isLetter() }

        when (command) {
            '-' -> box.removeIf { it.startsWith(label) }
            '=' -> {
                val focalLength = step.dropWhile { it.isLetter() }.drop(1).toInt()
                val currentLensIndex = box.indexOfFirst { it.startsWith(label) }
                if (currentLensIndex == -1) box.add("$label $focalLength")
                else box[currentLensIndex] = "$label $focalLength"
            }
        }
    }

    var result = 0
    for ((i, box) in boxes.withIndex()) {
        for ((j, lens) in box.withIndex()) {
            result += (i + 1) * (j + 1) * lens.dropWhile { it.isLetter() }.drop(1).toString().toInt()
        }
    }

    return result
}


private fun getBoxNumber(step: String): Int {
    val label = step.takeWhile { it.isLetter() }
    var boxNumber = 0
    for (char in label) {
        boxNumber += char.code
        boxNumber *= 17
        boxNumber %= 256
    }

    return boxNumber
}

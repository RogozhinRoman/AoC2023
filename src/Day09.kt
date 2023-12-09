fun main() {
    val lines = readInput("Day09")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val sequences = lines.map { it -> it.split(' ').map { it.toInt() } }

    val resultNumbers = mutableListOf<Int>()
    for (sequence in sequences) {
        val mutations = getSequenceMutations(sequence)
        mutations.add(0, sequence.toMutableList())

        for (i in mutations.lastIndex - 1 downTo 0) {
            val newElement = mutations[i + 1].last() + mutations[i].last()
            mutations[i].add(newElement)
        }

        resultNumbers.add(mutations.first().last())
    }

    return resultNumbers.sum()
}

private fun getSequenceMutations(sequence: List<Int>): MutableList<MutableList<Int>> {
    val mutationsList = mutableListOf<MutableList<Int>>()
    var currentSequence = sequence

    do {
        val differences = mutableListOf<Int>()
        for (i in 0 until currentSequence.lastIndex) {
            differences.add(currentSequence[i + 1] - currentSequence[i])
        }

        currentSequence = differences
        mutationsList.add(differences)
    } while (!differences.all { it == 0 })

    return mutationsList
}

private fun part2(lines: List<String>): Int {
    val sequences = lines.map { it -> it.split(' ').map { it.toInt() }.reversed() }

    val resultNumbers = mutableListOf<Int>()
    for (sequence in sequences) {
        val mutations = getSequenceMutations2(sequence)
        mutations.add(0, sequence.toMutableList())

        for (i in mutations.lastIndex - 1 downTo 0) {
            mutations[i].add(mutations[i].last() - mutations[i + 1].last())
        }

        resultNumbers.add(mutations.first().last())
    }

    return resultNumbers.sum()
}

private fun getSequenceMutations2(sequence: List<Int>): MutableList<MutableList<Int>> {
    val mutationsList = mutableListOf<MutableList<Int>>()
    var currentSequence = sequence

    do {
        val differences = mutableListOf<Int>()
        for (i in 0 until currentSequence.lastIndex) {
            differences.add(currentSequence[i] - currentSequence[i + 1])
        }

        currentSequence = differences
        mutationsList.add(differences)
    } while (!differences.all { it == 0 })

    return mutationsList
}


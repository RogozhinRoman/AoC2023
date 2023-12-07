fun main() {
    val lines = readInput("Day07")

    println(part1(lines))
    println(part2(lines))
}

private fun part1(lines: List<String>): Int {
    val cardsOrder = "A, K, Q, J, T, 9, 8, 7, 6, 5, 4, 3, 2".split(", ")
        .reversed()
        .mapIndexed { index, s -> s to index + 1 }
        .toMap()
    val bidByHand = lines.map { it.split(' ') }
        .associate { it[0] to it[1].toInt() }

    return calculateFinalBid(bidByHand, cardsOrder) { order: List<Int>, _: String -> getRank(order) }
}

private fun part2(lines: List<String>): Int {
    val cardsOrder = "A, K, Q, T, 9, 8, 7, 6, 5, 4, 3, 2, J".split(", ")
        .reversed()
        .mapIndexed { index, s -> s to index + 1 }
        .toMap()
    val bidByHand = lines.map { it.split(' ') }.associate { it[0] to it[1].toInt() }

    return calculateFinalBid(bidByHand, cardsOrder) { order: List<Int>, hand: String -> getFinalRank(order, hand) }
}

private fun getFinalRank(it: List<Int>, hand: String): Int {
    val initialRank = getRank(it)
    val jokersAmount = hand.count { it == 'J' }

    return when {
        jokersAmount == 0 || jokersAmount == 5 -> initialRank
        initialRank == 1 && jokersAmount == 1 -> initialRank + 2 // if one pair and one joker -> three of a kind
        initialRank == 2 && jokersAmount == 1 -> initialRank + 2 // if two pairs and one joker -> full house
        initialRank == 2 && jokersAmount == 2 -> initialRank + 3 // if two pairs and two jokers -> four of a kind
        initialRank == 3 && jokersAmount == 1 -> initialRank + 2 // if three of a kind and one joker -> four of a kind
        initialRank == 3 && jokersAmount == 2 -> initialRank + 2 // if three of a kind and two jokers -> five of a kind
        initialRank == 3 && jokersAmount == 3 -> 5               // if three of a kind and three jokers -> four of a kind
        initialRank == 4 && jokersAmount == 3 -> 6               // if full house and three jokers -> five of a kind
        jokersAmount == 4 -> 6
        else -> initialRank + jokersAmount
    }
}

private fun getRank(it: List<Int>): Int {
    return when {
        it[0] == 5 -> 6               // five of a kind
        it[0] == 4 -> 5               // four of a kind
        it[0] == 3 -> {
            if (it[1] == 2) 4         // full house
            else 3                    // three of a kind
        }
        it[0] == 2 && it[1] == 2 -> 2 // two pairs
        it[0] == 2 -> 1               // one pair
        else -> 0
    }
}

private fun calculateFinalBid(
    bidByHand: Map<String, Int>,
    cardsOrder: Map<String, Int>,
    rankCalculator: (List<Int>, String) -> Int
) = bidByHand.map { it.key }
    .map { it -> it to it.groupBy { it }.map { it.value.size }.sortedDescending() }
    .map { it.first to rankCalculator(it.second, it.first) }
    .sortedWith(getCardsComparator(cardsOrder))
    .mapIndexed { i, triple -> ((i + 1)) * bidByHand[triple.first]!! }
    .sum()

private fun getCardsComparator(cardsOrder: Map<String, Int>) =
    Comparator<Pair<String, Int>> { o1, o2 ->
        if (o1.second != o2.second) {
            return@Comparator o1.second - o2.second
        }

        val firstHand = o1.first
        val secondHand = o2.first

        for (i in 0..firstHand.length) {
            if (cardsOrder[firstHand[i].toString()]!! > cardsOrder[secondHand[i].toString()]!!) {
                return@Comparator 1
            } else if (cardsOrder[firstHand[i].toString()]!! < cardsOrder[secondHand[i].toString()]!!) {
                return@Comparator -1
            }
        }

        0
    }




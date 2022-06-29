package ru.smirnov.educational.leetcode.contest291

object II_MinimumConsecutiveCards {

    fun getMinimumCards(vararg cards: Int): Int {
        var minimumCards = -1
        val cardIndices = mutableMapOf<Int, Int>()

        cards.forEachIndexed { i, card ->
            if (cardIndices.containsKey(card)) {
                val prevIndex = cardIndices[card]!!

                val diff = (i - prevIndex) + 1
                if (minimumCards == -1 || diff < minimumCards) {
                    minimumCards = diff
                }
            }
            cardIndices[card] = i
        }
        return minimumCards
    }


}
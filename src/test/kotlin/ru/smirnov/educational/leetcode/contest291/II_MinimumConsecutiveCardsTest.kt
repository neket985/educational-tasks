package ru.smirnov.educational.leetcode.contest291

import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class II_MinimumConsecutiveCardsTest {

    @Test
    fun getMinimumCards() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1), DataAmount.ofMega(16),
            {
                assertEquals(
                    4,
                    II_MinimumConsecutiveCards.getMinimumCards(
                        3,4,2,3,4,7
                    )
                )
            },
            {
                assertEquals(
                    -1,
                    II_MinimumConsecutiveCards.getMinimumCards(
                        1,0,5,3
                    )
                )
            },
            {
                assertEquals(
                    3,
                    II_MinimumConsecutiveCards.getMinimumCards(
                        1,2,3,4,1,2,1
                    )
                )
            },
            {
                assertEquals(
                    2,
                    II_MinimumConsecutiveCards.getMinimumCards(
                        1,1
                    )
                )
            },
            {
                assertEquals(
                    -1,
                    II_MinimumConsecutiveCards.getMinimumCards(
                        1
                    )
                )
            },
            {
                assertEquals(
                    -1,
                    II_MinimumConsecutiveCards.getMinimumCards()
                )
            },
        )
    }
}
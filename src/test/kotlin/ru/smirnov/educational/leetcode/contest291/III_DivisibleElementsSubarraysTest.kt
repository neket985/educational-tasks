package ru.smirnov.educational.leetcode.contest291

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class III_DivisibleElementsSubarraysTest {

    @Test
    fun subarraysTest() {
        assertEquals(setOf("1,2,3", "1,2", "2,3", "1", "2", "3"), III_DivisibleElementsSubarrays.subarrays(1, 2, 3))
    }

    @Test
    fun countDistinct() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1), DataAmount.ofMega(100),
            {
                assertEquals(
                    11,
                    III_DivisibleElementsSubarrays.countDistinct(
                        2, 3, 3, 2, 2,
                        k = 2,
                        p = 2
                    )
                )
            },
            {
                assertEquals(
                    14,
                    III_DivisibleElementsSubarrays.countDistinct(
                        10, 2, 17, 7, 20,
                        k = 1,
                        p = 10
                    )
                )
            },
            {
                assertEquals(
                    10,
                    III_DivisibleElementsSubarrays.countDistinct(
                        1, 2, 3, 4,
                        k = 4,
                        p = 1
                    )
                )
            },
            {
                assertEquals(
                    20100,
                    III_DivisibleElementsSubarrays.countDistinct(
                        *(1..200).toList().toIntArray(),
                        k = 200,
                        p = 1
                    )
                )
            },
            {
                assertEquals(
                    10,
                    III_DivisibleElementsSubarrays.countDistinct(
                        *IntArray(200) { 2 },
                        k = 10,
                        p = 2
                    )
                )
            },
        )
    }
}
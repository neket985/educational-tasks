package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class L1BinarySearchTest {

    @Test
    fun minimumTime() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2), DataAmount.ofMega(256),
            {
                assertEquals(
                    4,
                    L1BinarySearch.process(
                        -1,0,3,5,9,12,
                        target = 9
                    )
                )
            },
            {
                assertEquals(
                    -1,
                    L1BinarySearch.process(
                        -1,0,3,5,9,12,
                        target = 2
                    )
                )
            },
            {
                assertEquals(
                    -1,
                    L1BinarySearch.process(
                        *IntArray(0),
                        target = 2
                    )
                )
            },
        )
    }
}
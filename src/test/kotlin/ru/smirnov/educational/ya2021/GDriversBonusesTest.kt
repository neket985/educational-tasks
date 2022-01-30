package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class GDriversBonusesTest {

    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1),
            DataAmount.ofMega(64),

            gDriversTestAssert(listOf(), 0),
            gDriversTestAssert(listOf(10), 500),
            gDriversTestAssert(listOf(1, 2, 3, 4), 5000),
            gDriversTestAssert(listOf(4, 3, 2, 1), 5000),
            gDriversTestAssert(listOf(5, 5, 5, 5), 2000),
            gDriversTestAssert(listOf(4, 2, 3, 3), 3000),
            gDriversTestAssert(listOf(1, 2, 3, 3, 3, 3, 1), 5500),
            gDriversTestAssert(listOf(1, 2, 3, 3, 3, 3, 10), 5500),
            gDriversTestAssert(listOf(1, 10, 20, 15, 14, 13, 10), 18 * 500),
        )
    }

    private fun gDriversTestAssert(list: List<Int>, result: Long): () -> Unit = {
        assertEquals(
            result,
            GDriversBonuses.process(
                list.size, list
            )
        )
    }
}
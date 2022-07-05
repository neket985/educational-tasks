package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class L1HashTableTest {

    @Test
    fun process() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(200), DataAmount.ofMega(256),
            {
                assertEquals(
                    1,
                    L1HashTable.process(1)
                )
            },
            {
                assertEquals(
                    4,
                    L1HashTable.process(4, 1, 2, 1, 2)
                )
            },
            {
                assertEquals(
                    1,
                    L1HashTable.process(2, 2, 1)
                )
            },
        )
    }
}
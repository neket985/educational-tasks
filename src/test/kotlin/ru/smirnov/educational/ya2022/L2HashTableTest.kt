package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class L2HashTableTest {

    @Test
    fun process() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(200), DataAmount.ofMega(256),
            {
                assertEquals(
                    listOf(0, 1),
                    L2HashTable.process(
                        2, 7, 11, 15,
                        target = 9
                    ).toList()
                )
            },
            {
                assertEquals(
                    listOf(1, 2),
                    L2HashTable.process(
                        3, 2, 4,
                        target = 6
                    ).toList()
                )
            },
            {
                assertEquals(
                    listOf(0, 1),
                    L2HashTable.process(
                        3, 3,
                        target = 6
                    ).toList()
                )
            },
        )
    }
}
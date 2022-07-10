package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class L1SortTest {

    @Test
    fun process() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(200), DataAmount.ofMega(256),
            {
                assertEquals(
                    arrayOf(
                        intArrayOf(1, 5),
                    ).joinToString { it.joinToString() },
                    L1Sort.merge(
                        intArrayOf(1, 4),
                        intArrayOf(4, 5),
                    ).joinToString { it.joinToString() }
                )
            },
            {
                assertEquals(
                    arrayOf(
                        intArrayOf(1, 5),
                        intArrayOf(7, 7),
                    ).joinToString { it.joinToString() },
                    L1Sort.merge(
                        intArrayOf(1, 2),
                        intArrayOf(2, 2),
                        intArrayOf(7, 7),
                        intArrayOf(2, 3),
                        intArrayOf(2, 3),
                        intArrayOf(3, 4),
                        intArrayOf(4, 5),
                    ).joinToString { it.joinToString() }
                )
            },
            {
                assertEquals(
                    arrayOf(
                        intArrayOf(1, 6),
                        intArrayOf(8, 10),
                        intArrayOf(15, 18),
                    ).joinToString { it.joinToString() },
                    L1Sort.merge(
                        intArrayOf(2, 6),
                        intArrayOf(8, 10),
                        intArrayOf(15, 18),
                        intArrayOf(1, 3),
                    ).joinToString { it.joinToString() }
                )
            },
        )
    }

    @Test
    fun quickSortTest() {
        assertEquals(
            intArrayOf(1, 2, 3, 4, 5, 6, 7, 8).joinToString(),
            arrayOf(2, 4, 6, 3, 5, 1, 7, 8).also {
                L1Sort.quickSort(it) { other ->
                    if (this > other) 1
                    else if (this < other) -1
                    else 0
                }
            }.joinToString()
        )
        assertEquals(
            intArrayOf(1, 2, 8, 15).joinToString(),
            arrayOf(2, 8, 15, 1).also {
                L1Sort.quickSort(it) { other ->
                    if (this > other) 1
                    else if (this < other) -1
                    else 0
                }
            }.joinToString()
        )
    }
}
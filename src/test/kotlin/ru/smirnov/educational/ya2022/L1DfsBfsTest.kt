package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class L1DfsBfsTest {

    @Test
    fun process() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(200), DataAmount.ofMega(256),
            {
                assertEquals(
                    1,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('1', '1', '1', '1', '0'),
                            charArrayOf('1', '1', '0', '1', '0'),
                            charArrayOf('1', '1', '0', '0', '0'),
                            charArrayOf('0', '0', '0', '0', '0')
                        )
                    )
                )
            },
            {
                assertEquals(
                    3,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('1', '1', '0', '0', '0'),
                            charArrayOf('1', '1', '0', '0', '0'),
                            charArrayOf('0', '0', '1', '0', '0'),
                            charArrayOf('0', '0', '0', '1', '1')
                        )
                    )
                )
            },
            {
                assertEquals(
                    0,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('0', '0', '0', '0', '0'),
                            charArrayOf('0', '0', '0', '0', '0'),
                            charArrayOf('0', '0', '0', '0', '0'),
                            charArrayOf('0', '0', '0', '0', '0')
                        )
                    )
                )
            },
            {
                assertEquals(
                    1,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('1', '1', '1'),
                            charArrayOf('1', '1', '1'),
                            charArrayOf('1', '1', '1'),
                            charArrayOf('1', '1', '1')
                        )
                    )
                )
            },
            {
                assertEquals(
                    1,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('1')
                        )
                    )
                )
            },

            {
                assertEquals(
                    5,
                    L1DfsBfs.process(
                        arrayOf(
                            charArrayOf('1', '0', '0', '0'),
                            charArrayOf('0', '1', '0', '0'),
                            charArrayOf('0', '0', '1', '0'),
                            charArrayOf('1', '0', '0', '1')
                        )
                    )
                )
            },
        )
    }
}
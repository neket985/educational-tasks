package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration
import java.util.*

internal class CMinesTest {

    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2),
            DataAmount.ofMega(256),
            listOf(
                { assertEquals(2, CMines.process(3, 3, 3, listOf(2 to 1, 2 to 2, 2 to 3))) },
                { assertEquals(1, CMines.process(3, 3, 2, listOf(2 to 1, 2 to 2))) },
                { assertEquals(0, CMines.process(1, 1, 1, listOf(1 to 1))) },
                { assertEquals(1, CMines.process(1, 1, 0, listOf())) },
                {
                    assertEquals(
                        2, CMines.process(
                            5, 5, 8, listOf(
                                2 to 2, 3 to 2, 4 to 2, 2 to 3, 4 to 3, 2 to 4, 3 to 4, 4 to 4
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        5, CMines.process(
                            8, 10, 22, listOf(
                                3 to 1, 6 to 1, 3 to 2, 6 to 2, 3 to 3, 4 to 3, 5 to 3, 6 to 3, 1 to 4, 2 to 4, 6 to 4,
                                6 to 5, 7 to 5, 8 to 5, 6 to 6, 1 to 7, 2 to 7, 3 to 7, 4 to 7, 5 to 7, 6 to 7, 5 to 10
                            )
                        )
                    )
                },
                { assertEquals(1, CMines.process(100, 100, 0, listOf())) }
            )
        )
    }

}
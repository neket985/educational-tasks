package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.DataUnit
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class BTechCreditTest {

    @Test
    fun process() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2),
            DataAmount.ofMega(256),
            listOf(
                { assertEquals(10, BTechCredit.process(6, 5, 10, listOf(1, 2, 3, 4, 5, 6))) },
                { assertEquals(27, BTechCredit.process(5, 7, 12, listOf(5, 22, 17, 13, 8))) },
                { assertEquals(1000000, BTechCredit.process(2, 1, 1000000, listOf(1, 1000000000))) },
            )
        )
    }

}
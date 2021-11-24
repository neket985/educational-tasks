package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class FDeliveryZonesTest {

    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1),
            DataAmount.ofMega(64),
            {
                assertEquals(
                    5,
                    FDeliveryZones.process(
                        4, 2, 3,
                        listOf(
                            listOf(4, -1, -3),
                            listOf(-2, 4, -3)
                        )
                    )
                )
            },
        )
    }
}
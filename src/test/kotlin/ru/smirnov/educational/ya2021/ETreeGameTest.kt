package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class ETreeGameTest {

    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(30),
            DataAmount.ofMega(256),
            listOf(
                {
                    assertEquals(
                        ETreeGame.Winner.First,
                        ETreeGame.process(
                            8, 1, "..-0..-+",
                            listOf(
                                1 to 2,
                                2 to 3,
                                2 to 4,
                                1 to 5,
                                5 to 6,
                                6 to 7,
                                6 to 8
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.Draw,
                        ETreeGame.process(
                            8, 1, "..-0..0+",
                            listOf(
                                1 to 2,
                                2 to 3,
                                2 to 4,
                                1 to 5,
                                5 to 6,
                                6 to 7,
                                6 to 8
                            )
                        )
                    )
                },
            )
        )
    }
}
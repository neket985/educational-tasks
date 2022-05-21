package ru.smirnov.educational.tinkoff2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import java.time.Duration

internal class Test {

    @Test
    fun process() {
        assertEquals(21, Main.calculateDeposit(6))
        assertEquals(37, Main.calculateDeposit(10))

        assertEquals(0, Main.calculateDeposit(0))
        assertEquals(0, Main.calculateDeposit(-10))
        assertEquals(1, Main.calculateDeposit(1))
        assertEquals(63, Main.calculateDeposit(14))
    }

}
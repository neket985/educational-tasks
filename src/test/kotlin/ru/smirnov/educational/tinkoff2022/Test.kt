package ru.smirnov.educational.tinkoff2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Test {

    @Test
    fun process() {
        assertEquals(21, PreTest_WeekDeposit.calculateDeposit(6))
        assertEquals(37, PreTest_WeekDeposit.calculateDeposit(10))

        assertEquals(0, PreTest_WeekDeposit.calculateDeposit(0))
        assertEquals(0, PreTest_WeekDeposit.calculateDeposit(-10))
        assertEquals(1, PreTest_WeekDeposit.calculateDeposit(1))
        assertEquals(63, PreTest_WeekDeposit.calculateDeposit(14))
    }

}
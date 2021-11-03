package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class BTechCreditTest {

    @Test
    fun process() {
        assertEquals(10, BTechCredit.process(6, 5, 10, listOf(1, 2, 3, 4, 5, 6)))
    }

    @Test
    fun findNearestLeftSide() {
    }
}
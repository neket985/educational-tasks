package ru.smirnov.educational.tinkoff

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.tinkoff.Test.findMaxCountDuplicatedElements

internal class TestTest {

    @Test
    fun findMaxCountDuplicatedElements() {
        assertEquals(3, findMaxCountDuplicatedElements(listOf(1, 1, 2), listOf(1, 2, 3)))
        assertEquals(4, findMaxCountDuplicatedElements(listOf(1, 2, 2), listOf(1, 2, 2)))
        assertEquals(3, findMaxCountDuplicatedElements(listOf(1, 2, 9, 10), listOf(2, 2, 10)))
        assertEquals(2, findMaxCountDuplicatedElements(listOf(), listOf(2, 2, 10, 11)))
        assertEquals(0, findMaxCountDuplicatedElements(listOf(), listOf()))
        assertEquals(2, findMaxCountDuplicatedElements(listOf(), listOf(1, 1)))
        assertEquals(4, findMaxCountDuplicatedElements(listOf(1), listOf(1, 1, 1)))
        assertEquals(1, findMaxCountDuplicatedElements(listOf(1, 2, 3), listOf()))
        assertEquals(1, findMaxCountDuplicatedElements(listOf(1, 2, 3), listOf(4, 5)))
        assertEquals(2, findMaxCountDuplicatedElements(listOf(1, 2, 3), listOf(4, 5, 5)))
    }
}
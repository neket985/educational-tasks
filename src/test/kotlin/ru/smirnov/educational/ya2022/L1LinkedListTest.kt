package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils.assertTimeLimitAndMemoryUsageLimit
import ru.smirnov.educational.ya2022.L1LinkedList.parse
import java.time.Duration

internal class L1LinkedListTest {

    @Test
    fun minimumTime() {
        assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2), DataAmount.ofMega(256),
            {
                assertEquals(
                    null,
                    L1LinkedList.process(Array(0) { null })
                )
            },
            {
                assertEquals(
                    null,
                    L1LinkedList.process(Array(1) { null })
                )
            },
            {
                assertEquals(
                    L1LinkedList.ListNode.valueOf(1, 1, 2, 3, 4, 4, 5, 6),
                    L1LinkedList.process(parse("[[1,4,5],[1,3,4],[2,6]]"))
                )
            },
            {
                assertEquals(
                    L1LinkedList.ListNode.valueOf(1, 1, 1, 2, 2, 2, 3, 3),
                    L1LinkedList.process(parse("[[1,2,3],[1,2,3],[1,2]]"))
                )
            },
        )
    }
}
package ru.smirnov.educational.leetcode.contest291

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import ru.smirnov.educational.leetcode.L2050ParallelCoursesIII
import java.time.Duration

internal class I_RemoveDigitFromNumberTest {
    @Test
    fun test(){
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(1), DataAmount.ofMega(16),
            {
                assertEquals(
                    "12",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "123",
                        '3'
                    )
                )
            },
            {
                assertEquals(
                    "1",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "12",
                        '2'
                    )
                )
            },
            {
                assertEquals(
                    "231",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "1231",
                        '1'
                    )
                )
            },
            {
                assertEquals(
                    "51",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "551",
                        '5'
                    )
                )
            },
            {
                assertEquals(
                    "13325",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "133235",
                        '3'
                    )
                )
            },
            {
                assertEquals(
                    "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                    I_RemoveDigitFromNumber.maxNumberAfterRemove(
                        "1111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111",
                        '1'
                    )
                )
            },
        )

    }
}
package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class Interview2Test {

    @Test
    fun findSubArraysTest() {
//        assertEquals(
//            1 to 2,
//            Interview2.findSubArray(
//                1, 2, 4, 5, 6,
//                x = 6
//            )
//        )
//        assertEquals(
//            -1 to -1,
//            Interview2.findSubArray(
//                *emptyArray<Int>().toIntArray(),
//                x = 6
//            )
//        )
//        assertEquals(
//            -1 to -1,
//            Interview2.findSubArray(
//                1, 2,
//                x = 6
//            )
//        )
//        assertEquals(
//            0 to 1,
//            Interview2.findSubArray(
//                1, 3, 4,
//                x = 4
//            )
//        )
//        assertEquals(
//            1 to 1,
//            Interview2.findSubArray(
//                1, 3, 4,
//                x = 3
//            )
//        )
//        assertEquals(
//            2 to 3,
//            Interview2.findSubArray(
//                1, 2, 3, 4, 5, 6, 7, 8, 9,
//                x = 7
//            )
//        )
//        assertEquals(
//            2 to 3,
//            Interview2.findSubArray(
//                1, 3, -3, 1, 1, 1, 1, 1, 1, 1,
//                x = -2
//            )
//        )

        assertEquals( //fixme тест не проходит
            -1 to -1,
            Interview2.findSubArray(
                1, 2, 4,
                x = -6
            )
        )
    }
}
package ru.smirnov.educational.ya2022

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

internal class Interview2Test {

    @Test
    fun findSubArraysTest() {
        assertEquals(
            1 to 2,
            Interview2.findSubArray(
                1, 2, 4, 5, 6,
                x = 6
            )
        )
        assertEquals(
            -1 to -1,
            Interview2.findSubArray(
                *emptyArray<Int>().toIntArray(),
                x = 6
            )
        )
        assertEquals(
            -1 to -1,
            Interview2.findSubArray(
                1, 2,
                x = 6
            )
        )
        assertEquals(
            0 to 1,
            Interview2.findSubArray(
                1, 3, 4,
                x = 4
            )
        )
        assertEquals(
            1 to 1,
            Interview2.findSubArray(
                1, 3, 4,
                x = 3
            )
        )
        assertEquals(
            2 to 3,
            Interview2.findSubArray(
                1, 2, 3, 4, 5, 6, 7, 8, 9,
                x = 7
            )
        )
        assertEquals(
            2 to 3,
            Interview2.findSubArray(
                1, 3, -3, 1, 1, 1, 1, 1, 1, 1,
                x = -2
            )
        )
        assertEquals(
            -1 to -1,
            Interview2.findSubArray(
                1, 2, 4,
                x = -6
            )
        )
        assertEquals(
            -1 to -1,
            Interview2.findSubArray(
                25, 1, 11, 14, -15, 19, -17, 21, 26, -7,
                x = 7
            )
        )
        assertEquals(
            -1 to -1,
            Interview2.findSubArray(
                -79, -76, -78, -75, -77, -76, -72, -76, -73, -82,
                x = 0
            )
        )
        assertEquals(
            0 to 5,
            Interview2.findSubArray(
                -79, -76, -78, 79, 76, 78, -75, -77, -76, -72,
                x = 0
            )
        )

        testRandoms()
    }

    private fun testRandoms(trueTestCount: Int = 100) {
        var trueTests = 0
        while (trueTests < trueTestCount) {
            val rndArray = randomArray()
            val rndX = Random.nextInt(-1000, 1000)
            println("rndArr: ${rndArray.joinToString()}")
            println("rndX: $rndX")
            val result = Interview2.findSubArray(
                *rndArray,
                x = rndX
            )
            println("result: $result")
            checkResult(rndArray, rndX, result)?.also {
                assert(it)
                ++trueTests
            }
        }
    }

    private fun checkResult(intArray: IntArray, x: Int, result: Pair<Int, Int>): Boolean? {
        if (result.first > result.second) return false
        if (result.first == -1 && result.second == -1) return null //не можем точно знать (сложно проверить)

        var sum = 0
        (result.first..result.second).forEach { idx ->
            sum += intArray[idx]
        }

        return sum == x
    }

    private fun randomArray(
        length: Int = Random.nextInt(100),
        valueRange: IntRange = (Random.nextInt(-100, 100) to Random.nextInt(-100, 100)).let { (f, s) ->
            if (f > s) s..f
            else if (f == s) f - 1..s
            else f..s
        }
    ): IntArray {
        return IntArray(length) {
            Random.nextInt(valueRange.first, valueRange.last)
        }
    }
}
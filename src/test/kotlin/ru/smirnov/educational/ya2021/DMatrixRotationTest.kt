package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration
import kotlin.math.absoluteValue
import kotlin.math.withSign

internal class DMatrixRotationTest {

    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2),
            DataAmount.ofMega(256),
            listOf(
//                {
//                    assert(
//                        listOf(
//                            listOf(-1.0, 0.0),
//                            listOf(1.0, 0.0)
//                        ).roundContains(
//                            DMatrixRotation.process(2, listOf(listOf(0.0, 1.0)))
//                        )
//                    )
//                },
                {
                    assert(
                        listOf(
                            listOf(-0.666666666667, 0.333333333333, 0.666666666667),
                            listOf(0.666666666667, -0.333333333333, -0.666666666667)
                        ).roundContains(
                            DMatrixRotation.process(
                                3,
                                listOf(
                                    listOf(0.6666666666666, 0.6666666666666, 0.3333333333333),
                                    listOf(-0.3333333333333, 0.6666666666666, -0.6666666666666)
                                )
                            )
                        )
                    )
                },
                //todo 16x matrix
            )
        )
    }

    @Test
    fun gaussianMatrixTest(){
        val matrix = DMatrixRotation.Matrix(
            listOf(
                DMatrixRotation.MatrixRow(listOf(0.0, 0.0, 1.0), 1.0),
                DMatrixRotation.MatrixRow(listOf(1.0, 2.0, 3.0), 2.0),
                DMatrixRotation.MatrixRow(listOf(4.0, 1.0, 0.0), 3.0),
            )
        )

        val result = matrix.toGaussianViewMatrix()
        assertEquals(0.0, result.elements[1].first[0].absoluteValue)
        assertEquals(0.0, result.elements[2].first[0].absoluteValue)
        assertEquals(0.0, result.elements[2].first[1].absoluteValue)
    }

    //один из списков ответов содержит ответ, совподающий с полученным с точностью до 6 знаков
    private fun List<List<Double>>.roundContains(item: List<Double>) =
        any { list ->
            if (list.size != item.size) return@any false
            list.forEachIndexed { index, d ->
                if (!roundEquals(d, item[index])) return@any false
            }
            return@any true
        }

    private fun roundEquals(first: Double, second: Double) =
        (first * 1000000).toInt() == (second * 1000000).toInt()

}
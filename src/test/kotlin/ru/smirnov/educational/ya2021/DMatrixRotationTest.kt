package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import ru.smirnov.educational.ya2021.DMatrixRotation.matrixGaussian
import java.time.Duration
import java.util.concurrent.atomic.AtomicReference
import kotlin.math.absoluteValue
import kotlin.math.pow

internal class DMatrixRotationTest {

    @Test
    fun process() {

        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(30),
            DataAmount.ofMega(256),
            listOf(
                {
                    assert(
                        listOf(
                            listOf(-1.0, 0.0),
                            listOf(1.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(2, listOf(listOf(0.0, 1.0))), 6
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(0.0, -1.0, 0.0, 0.0),
                            listOf(0.0, 1.0, 0.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(
                                4, listOf(
                                    listOf(0.0, 0.0, 0.0, 1.0),
                                    listOf(1.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 1.0, 0.0)
                                )
                            ),
                            6
                        )
                    )
                },
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
                            ),
                            6
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(-0.22919, -0.41411, 0.01141, -0.30659, 0.82575),
                            listOf(0.22919, 0.41411, -0.01141, 0.30659, -0.82575)
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(-0.24045, -0.17761, 0.01603, -0.83299, -0.46531),
                                    listOf(-0.94274, 0.12031, 0.00566, 0.29741, -0.09098),
                                    listOf(-0.02069, 0.30417, -0.93612, -0.13759, 0.10865),
                                    listOf(0.02155, -0.83065, -0.35109, 0.32365, -0.28556),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    assert(
                        listOf(
                            listOf(0.07863783, 0.7048799, 0.08914089, -0.64230492, -0.27651168),
                            listOf(-0.07863783, -0.7048799, -0.08914089, 0.64230492, 0.27651168),
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(-0.63470728, 0.41984536, 0.41569193, 0.25708079, 0.42659843),
                                    listOf(-0.36418389, 0.06244462, -0.82734663, -0.24066123, 0.3479231),
                                    listOf(0.67691426, 0.33798442, -0.05984083, 0.17555011, 0.62702062),
                                    listOf(-0.01095148, -0.45688226, 0.36217501, -0.65773717, 0.47681205)
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    val angle = 3.1415926 / 15
                    assert(
                        listOf(
                            listOf(0.0, Math.sin(angle), 0.0, 0.0, Math.cos(angle)),
                            listOf(0.0, -Math.sin(angle), 0.0, 0.0, -Math.cos(angle)),
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(1.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 1.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 1.0, 0.0),
                                    listOf(0.0, Math.cos(angle), 0.0, 0.0, -Math.sin(angle)),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    val angle = 3.1415926 / 12
                    assert(
                        listOf(
                            listOf(0.0, 0.0, 0.0, 1.0, 0.0),
                            listOf(0.0, 0.0, 0.0, -1.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(
                                5,
                                listOf(
                                    listOf(1.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, Math.sin(angle), Math.cos(angle), 0.0, 0.0),
                                    listOf(0.0, Math.cos(angle), -Math.sin(angle), 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 1.0),
                                )
                            ),
                            4
                        )
                    )
                },
                {
                    val angle = 3.1415926 / 12
                    assert(
                        listOf(
                            listOf(0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                            listOf(0.0, 0.0, 0.0, -1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
                        ).roundContains(
                            DMatrixRotation.process(
                                16,
                                listOf(
                                    listOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, Math.sin(angle), Math.cos(angle), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, Math.cos(angle), -Math.sin(angle), 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0),
                                    listOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0)
                                )
                            ),
                            4
                        )
                    )
                },
                //todo 16x matrix
            )
        )
    }

    @Test
    fun gaussianMatrixTest() {
        val matrix =
            listOf(
                listOf(0.0, 0.0, 1.0, 1.0).mapIndexed{ i, it -> DeterminantContainer(i, it)},
                listOf(1.0, 2.0, 3.0, 2.0).mapIndexed{ i, it -> DeterminantContainer(i, it)},
                listOf(4.0, 1.0, 0.0, 3.0).mapIndexed{ i, it -> DeterminantContainer(i, it)}
            )

        val result = matrixGaussian(4, matrix)
        assertEquals(0.0, result[1][0].value.absoluteValue)
        assertEquals(0.0, result[2][0].value.absoluteValue)
        assertEquals(0.0, result[2][1].value.absoluteValue)
    }

    //один из списков ответов содержит ответ, совподающий с полученным с точностью до {{sign}} знаков
    private fun List<List<Double>>.roundContains(item: List<Double>, sign: Int) =
        any { list ->
            if (list.size != item.size) return@any false
            list.forEachIndexed { index, d ->
                if (!roundEquals(d, item[index], sign)) return@any false
            }
            return@any true
        }

    private fun roundEquals(first: Double, second: Double, sign: Int) =
        ((first - second)* 10.0.pow(sign)).toInt() == 0

}
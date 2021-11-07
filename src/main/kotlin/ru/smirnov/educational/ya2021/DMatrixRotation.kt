package ru.smirnov.educational.ya2021

import kotlin.math.pow

object DMatrixRotation {

    fun process(N: Int, matrix: List<List<Double>>): List<Double> {
        val m = Matrix(matrix.map { list -> MatrixRow(list, 0.0) }).toGaussianViewMatrix()

        val xByLastX = DoubleArray(N) { 1.0 }
        (0..N - 2).reversed().forEach { i ->
            val row = m.elements[i]
            var lastXSum = 0.0
            (i + 1..N - 1).forEach {
                lastXSum += row.first[it] * xByLastX[it]
            }
            xByLastX[i] = -lastXSum / row.first[i]
        }

        val lastX2SumForLastMRow = xByLastX.reduceIndexed { index, acc, d ->
            if(index==1) acc*acc + d*d
            else acc + d*d
        }

        val lastX = (1/lastX2SumForLastMRow).pow(1.0/2) //todo +- | два варианта

        return xByLastX.map {
            it * lastX
        }
    }

    data class MatrixRow(
        val first: List<Double>,
        val second: Double
    ) {

        operator fun plus(other: MatrixRow) =
            MatrixRow(
                first.mapIndexed { index, d ->
                    d + other.first[index]
                },
                (second + other.second)
            )

        operator fun times(multiplier: Double) =
            MatrixRow(
                first.map { d ->
                    d * multiplier
                },
                (second * multiplier)
            )
    }

    data class Matrix(
        val elements: List<MatrixRow>,
    ) {
        private val size = elements.size

        fun toGaussianViewMatrix(): Matrix {
            val sortedPreFilledElements = elements.toMutableList()

            (0 until size).forEach { index ->
                val it = sortedPreFilledElements[index].let {
                    if (it.first[index] == 0.0) {
                        var plus: MatrixRow? = null
                        pl@ for (it in index + 1 until size) {
                            val e = sortedPreFilledElements[it]
                            if (e.first[index] != 0.0) {
                                plus = e
                                break@pl
                            }
                        }
                        if (plus == null) throw IllegalStateException("not found addition")
                        (it + plus!!).apply {
                            sortedPreFilledElements[index] = this
                        }
                    } else it
                }
                (index + 1 until size).forEach { indexOther ->
                    val other = sortedPreFilledElements[indexOther]
                    val i1 = it.first[index]
                    val i2 = other.first[index]
                    if (i2 != 0.0) {
                        val negativeOther = it * (i2 * -1)
                        sortedPreFilledElements[indexOther] = (other * i1 + negativeOther) * (1 / i1)
                    }
                }
            }
            return Matrix(sortedPreFilledElements)
        }

    }
}
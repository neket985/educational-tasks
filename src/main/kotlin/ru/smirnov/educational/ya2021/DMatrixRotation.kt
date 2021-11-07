package ru.smirnov.educational.ya2021

import kotlin.math.pow

object DMatrixRotation {

    fun process(N: Int, matrix: List<List<Double>>): List<Double> {
//        val isOneBasedMatrix = matrix[0].any { it==1.0 } //todo корректно работать с матрицами, в которых есть 0 (не все они единичные)
        val isOneBasedMatrix = false
        if(isOneBasedMatrix){
            var lastRowIndexOne = -1
            for(i in (0 until N)){
                if(!matrix.any { it[i]==1.0 }){
                    lastRowIndexOne = i
                    break
                }
            }
            return (0 until N).map{if(it==lastRowIndexOne) 1.0 else 0.0}
        }else {
            val m = Matrix(matrix.map { list -> MatrixRow(list) }).toGaussianViewMatrix()

            val xByLastX = DoubleArray(N) { 1.0 }
            (0..N - 2).reversed().forEach { i ->
                val row = m[i]
                var lastXSum = 0.0
                (i + 1..N - 1).forEach {
                    lastXSum += row[it] * xByLastX[it]
                }
                xByLastX[i] = -lastXSum / row[i]
            }

            val lastX2SumForLastMRow = xByLastX.reduceIndexed { index, acc, d ->
                if (index == 1) acc * acc + d * d
                else acc + d * d
            }

            val lastX = (1 / lastX2SumForLastMRow).pow(1.0 / 2) //todo +- | два варианта

            return xByLastX.map {
                it * lastX
            }
        }
    }

    data class MatrixRow(
        val values: List<Double>
    ) {
        operator fun get(index: Int) = values[index]

        operator fun plus(other: MatrixRow) =
            MatrixRow(
                values.mapIndexed { index, d ->
                    d + other.values[index]
                }
            )

        operator fun times(multiplier: Double) =
            MatrixRow(
                values.map { d ->
                    d * multiplier
                }
            )
    }

    data class Matrix(
        val elements: List<MatrixRow>,
    ) {
        private val size = elements.size

        operator fun get(index: Int) = elements[index]

        fun toGaussianViewMatrix(): Matrix {
            val sortedPreFilledElements = elements.toMutableList()

            (0 until size).forEach { index ->
                val it = sortedPreFilledElements[index].let {
                    if (it[index] == 0.0) {
                        var plus: MatrixRow? = null
                        pl@ for (it in index + 1 until size) {
                            val e = sortedPreFilledElements[it]
                            if (e[index] != 0.0) {
                                plus = e
                                break@pl
                            }
                        }
                        if (plus == null) throw IllegalStateException("it's one based matrix")
                        (it + plus).apply {
                            sortedPreFilledElements[index] = this
                        }
                    } else it
                }
                (index + 1 until size).forEach { indexOther ->
                    val other = sortedPreFilledElements[indexOther]
                    val i1 = it[index]
                    val i2 = other[index]
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
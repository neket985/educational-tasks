package ru.smirnov.educational.ya2021

object DMatrixRotation {

    fun process(N: Int, matrix: List<List<Double>>): List<Double> {
        return recursiveGettingDeterminantVector(N, matrix.map {
            it.mapIndexed { index, d ->
                DeterminantContainer(index, d)
            }
        }).sortedBy { it.xIndex }.map { it.value }
    }

    //сложность n^2
    private fun recursiveGettingDeterminantVector(
        n: Int,
        matrix: List<List<DeterminantContainer>>
    ): List<DeterminantContainer> = run {
        if (n == 2) {
            listOf(
                DeterminantContainer(matrix[0][0].xIndex, -matrix[0][1].value),
                DeterminantContainer(matrix[0][1].xIndex, matrix[0][0].value)
            )
        } else {
            val matrixGaussian = matrixGaussian(n, matrix)
//            val matrixGaussian = matrix
            val baseRowIndex = n - 2
            val emptyVector = (0 until n).map { i -> DeterminantContainer(i, 0.0) }
            (0 until n).flatMap { i ->
                val sign = if ((baseRowIndex + i) % 2 == 0) +1 else -1
                val base = matrixGaussian[baseRowIndex][i].value * sign
                if (base == 0.0) return@flatMap emptyVector

                val subMatrixIndexes = (0 until n).toMutableList().apply { remove(i) }

                val newMatrix = (0 until n - 2).map { subMatrixRowIndex ->
                    subMatrixIndexes.map { subMatrixIndex ->
                        matrixGaussian[subMatrixRowIndex][subMatrixIndex]
                    }
                }

                recursiveGettingDeterminantVector(n - 1, newMatrix).map {
                    it.copy(value = it.value * base)
                }
            }.groupBy { it.xIndex }.map { (xIndex, values) ->
                DeterminantContainer(xIndex, values.sumByDouble { it.value })
            }
        }
    }

    fun matrixGaussian(
        n: Int,
        matrix: List<List<DeterminantContainer>>
    ): List<List<DeterminantContainer>> {
        val newMatrix = matrix.toMutableList()
        (0 until n - 2).forEach { index ->
            val it = newMatrix[index].let {
                if (it[index].value == 0.0) {
                    var plus: List<DeterminantContainer>? = null
                    pl@ for (it in index + 1 until n - 1) {
                        val e = newMatrix[it]
                        if (e[index].value != 0.0) {
                            plus = e
                            break@pl
                        }
                    }
                    plus?.run { (it + this).apply { newMatrix[index] = this } } ?: it
                } else it
            }
            (index + 1 until n - 1).forEach { indexOther ->
                val other = newMatrix[indexOther]
                val i1 = it[index].value
                val i2 = other[index].value
                if (i2 != 0.0) {
                    val negativeOther = it * (i2 * -1)
                    newMatrix[indexOther] = (other * i1 + negativeOther) * (1 / (i1))
                }
            }
        }

        return newMatrix
    }

    operator fun List<DeterminantContainer>.plus(other: List<DeterminantContainer>) =
        this.plus(other.toTypedArray()).groupBy { it.xIndex }
            .map {
                DeterminantContainer(it.key, it.value.sumByDouble { it.value })
            }

    operator fun List<DeterminantContainer>.times(multiplier: Double) =
        this.map { d ->
            d.copy(value = d.value * multiplier)
        }
}

data class DeterminantContainer(
    val xIndex: Int,
    val value: Double
)
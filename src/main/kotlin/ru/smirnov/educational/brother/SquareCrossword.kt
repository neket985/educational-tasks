package ru.smirnov.educational.brother

import me.tongfei.progressbar.*
import ru.smirnov.educational.common.CustomConsoleProgressBarConsumer
import java.time.Duration

object SquareCrossword {

    fun buildCrossword(n: Int, words: List<String>): List<String> {
        return CrossMatrix(n, words).run {
            initCrossMatrix()
            stepOnVariants()
        }

    }

    class CrossMatrix(val n: Int, private val words: List<String>) {
        val crossMatrix = Array(2 * n) {
            CrossMatrixRow(n)
        }

        // сложность O(n^4) 0_0
        fun initCrossMatrix() {
            words.forEachIndexed { firstI, firstWord ->
                (firstI + 1 until 2 * n).forEach { secondI ->
                    val secondWord = words[secondI]
                    (0 until n).forEach { firstWordCharI ->
                        (0 until n).forEach { secondWordCharI ->
                            if (firstWord[firstWordCharI] == secondWord[secondWordCharI]) {
                                crossMatrix[firstI].addCrossIndex(secondI, secondWordCharI)
                                crossMatrix[secondI].addCrossIndex(firstI, firstWordCharI)
                            }
                        }
                    }
                }
            }
        }

        private fun buildPositionVariants() = (0 until 2 * n).map { i ->
            crossMatrix[i].getIndexesWithMaxWeight().toList()
        }

        fun stepOnVariants(): List<String> {
            val positionVariants = buildPositionVariants()

            return checkVariantReq(positionVariants, 0, mapOf(), mapOf(), mapOf())!!.map { words[it] }
        }

        fun checkVariantReq(
            positionVariants: List<List<Int>>,
            wordI: Int,
            directionMap: Map<Int, Boolean>,
            horizontalWordsPositions: Map<Int, Int>,
            verticalWordsPositions: Map<Int, Int>,
        ): List<Int>? {
            val directions = directionMap[wordI]?.let(::listOf) ?: listOf(true, false)

            return useProgressBar("task word $wordI", directions.size.toLong() * positionVariants[wordI].size) { bar ->
                directionLoop@ for (direction in directions) {
                    val wordsPositions = if (direction) horizontalWordsPositions else verticalWordsPositions
                    val positions = positionVariants[wordI].filter { !wordsPositions.containsKey(it) }
                    bar.stepBy(positionVariants[wordI].size - positions.size.toLong())
                    positionLoop@ for (position in positions) {
                        bar.step()
                        val horizontalWordsPositionsCopy = horizontalWordsPositions.toMutableMap()
                        val verticalWordsPositionsCopy = verticalWordsPositions.toMutableMap()
                        val directionMapCopy = directionMap.toMutableMap()
                        val sameDirectionWords = crossMatrix[wordI].row.mapIndexedNotNull { i, pos ->
                            if (pos.contains(position) || i == wordI) null
                            else i
                        }
                        for (sameDirectionWord in sameDirectionWords) {
                            val existsDirection = directionMapCopy[sameDirectionWord]
                            if (existsDirection != null && existsDirection != direction) {
                                continue@positionLoop
                            }
                            directionMapCopy[sameDirectionWord] = direction
                        }

                        val wordsPositionsCopy =
                            if (direction) horizontalWordsPositionsCopy else verticalWordsPositionsCopy
                        wordsPositionsCopy.apply { put(position, wordI) }

                        if (wordI == 2 * n - 1) {
                            val resultList = horizontalWordsPositionsCopy.entries.sortedBy { it.key }.map { it.value }
                            return@useProgressBar if (finalCheck(resultList)) resultList else null
                        } else {
                            checkVariantReq(
                                positionVariants,
                                wordI + 1,
                                directionMapCopy.apply { put(wordI, direction) },
                                horizontalWordsPositionsCopy,
                                verticalWordsPositionsCopy
                            )?.also {
                                return@useProgressBar it
                            }
                        }
                    }
                }

                return@useProgressBar null
            }
        }

        //todo где-то не хватает валидаций. из-за этого приходится дополнительно проверять корректность получившейся матрицы на финальном этапе
        private fun finalCheck(result: List<Int>): Boolean {
            val resultMatrix = result.map { words[it] }
            val resultWords = parseWordsFromMatrix(resultMatrix.toMatrix())
            val wordsDifference = words.toMutableList()
            resultWords.forEach {
                wordsDifference.remove(it)
            }
            return wordsDifference.isEmpty()
        }

        private fun List<String>.toMatrix(): Array<CharArray> {
            val n = size
            return Array(n) { i ->
                this[i].toCharArray()
            }
        }

        private fun parseWordsFromMatrix(matrix: Array<CharArray>): List<String> {
            val n = matrix.size
            val sb = StringBuilder()
            return (0 until n).flatMap { i ->
                (0 until n).forEach { j ->
                    sb.append(matrix[i][j])
                }
                val hWord = sb.toString()
                sb.clear()

                (0 until n).forEach { j ->
                    sb.append(matrix[j][i])
                }
                val vWord = sb.toString()
                sb.clear()
                listOf(hWord, vWord)
            }
        }
    }

    class CrossMatrixRow(val n: Int) {
        val row = Array<MutableSet<Int>>(2 * n) {
            mutableSetOf()
        }
        private val indexesCount = mutableMapOf<Int, Int>() //position to count

        fun addCrossIndex(otherWordI: Int, otherWordCharI: Int) {
            indexesCount.compute(otherWordCharI) { _, oldValue ->
                oldValue?.inc() ?: 1
            }
            row[otherWordI].add(otherWordCharI)
        }

        fun getIndexesWithMaxWeight(): Set<Int> {
            val keys = indexesCount.filterValues { it >= n }.keys
            if (keys.isEmpty()) throw IllegalStateException("cross word can not be solved")
            return keys
        }

        override fun toString() = row.joinToString(",")
    }

    private val consumersMap = mutableMapOf<String, InteractiveConsoleProgressBarConsumer>()
    private fun <T> useProgressBar(name: String, maxNumbers: Long, exec: (ProgressBar) -> T): T {
        val consumer = consumersMap.getOrPut(name) { CustomConsoleProgressBarConsumer(System.out) }
        return ProgressBarBuilder()
            .setStyle(ProgressBarStyle.ASCII)
            .setTaskName(name)
            .setInitialMax(maxNumbers)
            .setConsumer(consumer)
            .continuousUpdate()
            .setUpdateIntervalMillis(Duration.ofSeconds(1).toMillis().toInt())
            .build()
            .use {
                exec(it)
            }
    }
}
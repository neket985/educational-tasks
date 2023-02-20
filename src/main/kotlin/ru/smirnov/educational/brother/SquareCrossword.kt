package ru.smirnov.educational.brother

import me.tongfei.progressbar.DelegatingProgressBarConsumer
import me.tongfei.progressbar.ProgressBar
import me.tongfei.progressbar.ProgressBarBuilder
import me.tongfei.progressbar.ProgressBarStyle
import java.math.BigInteger
import java.util.*

object SquareCrossword {

    fun buildCrossword(n: Int, words: List<String>): List<String> {
        return CrossMatrix(n, words).run {
            initCrossMatrix()
            stepOnVariants().map {
                words[it]
            }.also {
                println("result: \n${it.joinToString("\n")}")
            }
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

        fun stepOnVariants(): List<Int> {
            val positionVariants = buildPositionVariants()

            val variantsSequence = sequence {
                val totalVariantsCount = positionVariants.fold(BigInteger.ONE){ acc, element ->
                    acc * element.size.toBigInteger()
                }
                println("Total variants: $totalVariantsCount")
                ProgressBarBuilder()
                    .setStyle(ProgressBarStyle.ASCII)
                    .setInitialMax(totalVariantsCount.toLong())
                    .setTaskName("Variants task")
                    .setConsumer(DelegatingProgressBarConsumer(::println))
                    .build()
                    .use { bar ->
                        val indexes = VariantsIterationIndexes(positionVariants)
                        val stateIndexes = indexes.state
                        loop@ while (true) {
                            val variants = mutableListOf<PositionVariant>()
                            for (i in 0 until 2 * n) {
                                val pos = positionVariants[i]
                                val variant = PositionVariant(
                                    pos[stateIndexes[i].i],
                                    stateIndexes[i].direction
                                )
                                if (variants.contains(variant)) { //если такая позиция уже занята
                                    bar.step()
                                    indexes.next()
                                    continue@loop
                                }
                                variants.add(i, variant)
                            }

                            bar.step()
                            this.yield(variants)
                            indexes.next()
                        }
                    }
            }

            return checkVariant(variantsSequence)
        }

        fun checkVariant(variantsSequence: Sequence<List<PositionVariant>>): List<Int> {
            variantLoop@ for (variant in variantsSequence) {
                val checkMarks = LinkedList((0 until 2 * n).toList())
                val stack: Stack<Int> = Stack()
                val validation = CrossMatrixValidation(crossMatrix)

                while (checkMarks.isNotEmpty()) {
                    stack.add(checkMarks.poll())
                    while (stack.isNotEmpty()) {
                        val wordI = stack.pop()
                        val position = variant[wordI].pos
                        val isHorizontalDirection = variant[wordI].direction
                        val (result, sameDirectionWords) = validation.validate(wordI, position, isHorizontalDirection)

                        if (!result) continue@variantLoop
                        val notCheckedSameDirectionWords = sameDirectionWords.filter { checkMarks.remove(it) }
                        stack.addAll(notCheckedSameDirectionWords)
                    }
                }

                return validation.getResultMatrix()
            }

            throw IllegalStateException("Correct variant is not found")
        }
    }


    class CrossMatrixValidation(
        private val crossMatrix: Array<CrossMatrixRow>
    ) {
        private val directionMap = mutableMapOf<Int, Boolean>() //word to isHorizontal

        //position to wordI
        private val horizontalWordsPositions = mutableMapOf<Int, Int>()
        private val verticalWordsPositions = mutableMapOf<Int, Int>()


        fun validate(
            wordI: Int,
            position: Int,
            isHorizontalDirection: Boolean
        ): Pair<Boolean, List<Int>> { //validationResult to list words indexes at the same direction
            directionMap[wordI]?.also {
                if (it != isHorizontalDirection)
                    return false to emptyList()
            }

            val wordsPositions =
                if (isHorizontalDirection) horizontalWordsPositions
                else verticalWordsPositions

            if (wordsPositions.containsKey(position))
                return false to emptyList()

            val sameDirectionWords = crossMatrix[wordI].row.mapIndexedNotNull { i, pos ->
                if (pos.contains(position) || i == wordI) null
                else i
            }
            sameDirectionWords.forEach {
                directionMap[it]?.also {
                    if (it != isHorizontalDirection)
                        return false to emptyList()
                }
                directionMap[it] = isHorizontalDirection
            }

            wordsPositions[position] = wordI
            return true to sameDirectionWords
        }

        fun getResultMatrix() = horizontalWordsPositions.entries.sortedBy { it.key }
            .map { it.value }
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

    class VariantsIterationIndexes(positionVariants: List<List<Int>>) {
        val size = positionVariants.size
        val state = Array(positionVariants.size) { VariantIndex() }

        private val sizes = positionVariants.map { it.size }

        fun next(): Array<VariantIndex> {
            (0 until size).reversed().forEach { i ->
                if (state[i].isLast(sizes[i] - 1)) {
                    state[i].reset()
                } else {
                    state[i].next()
                    return state
                }
            }

            throw IllegalStateException("iterator already ending")
        }
    }

    data class VariantIndex(
        var i: Int,
        var direction: Boolean
    ) {
        constructor() : this(0, false)

        fun next() {
            if (direction) {
                ++i
                direction = false
            } else {
                direction = true
            }
        }

        fun isLast(lastI: Int) = i == lastI && direction

        fun reset() {
            i = 0
            direction = false
        }
    }

    data class PositionVariant(
        val pos: Int,
        val direction: Boolean
    )
}
package ru.smirnov.educational.brother

import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class SquareCrosswordTest {

    @Test
    fun buildCrossword() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(10000), DataAmount.ofMega(256),
            {
                assertCheck(
                    listOf("A", "A"),
                )
            },
            {
                assertCheck(
                    listOf(
                        "ACE",
                        "ICE",
                        "FAN",
                        "FIG",
                        "NET",
                        "GET"
                    )
                )
            },
            {
                assertCheck(
                    listOf(
                        "AA",
                        "AB",
                        "AB",
                        "AA"
                    )
                )
            },
            {
                assertCheck(
                    listOf(
                        "ABB",
                        "BBB",
                        "ABA",
                        "AAB",
                        "AAA",
                        "AAB"
                    )
                )
            },
            {
                val n = 8
                val matrix = generateMatrix(n)
                println("matrix: \n${matrix.toListString().joinToString("\n")}")
                println("words: \n${parseWordsFromMatrix(matrix).joinToString("\n")}")
                assertCheck(
                    parseWordsFromMatrix(matrix),
                )
            },
        )
    }

    private fun assertCheck(inputWords: List<String>) {
        val result = SquareCrossword.buildCrossword(inputWords.size / 2, inputWords).also {
            println("result: \n${it.joinToString("\n")}")
        }
        val resultWords = parseWordsFromMatrix(result.toMatrix())
        val wordsDifference = inputWords.toMutableList()
        resultWords.forEach {
            wordsDifference.remove(it)
        }
        assert(wordsDifference.isEmpty())
    }

    private val wordsAlphabet = 'A'..'Z'
    private fun generateMatrix(n: Int): Array<CharArray> = Array(n) {
        CharArray(n) {
            wordsAlphabet.random()
        }
    }

    private fun List<String>.toMatrix(): Array<CharArray> {
        val n = size
        return Array(n) { i ->
            this[i].toCharArray()
        }
    }

    private fun Array<CharArray>.toListString(): List<String> {
        val n = size
        return (0 until n).map { i ->
            String(this[i])
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
        }.shuffled()
    }
}

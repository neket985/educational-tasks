package ru.smirnov.educational.brother

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import java.time.Duration

internal class SquareCrosswordTest {

    @Test
    fun buildCrossword() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(10000), DataAmount.ofMega(256),
//            {
//                assertEquals(
//                    listOf("A"),
//                    SquareCrossword.buildCrossword(
//                        1,
//                        listOf("A")
//                    )
//                )
//            },
//            {
//                assertEquals(
//                    listOf(
//                        "FAN",
//                        "ICE",
//                        "GET"
//                    ),
//                    SquareCrossword.buildCrossword(
//                        3,
//                        listOf(
//                            "ACE",
//                            "ICE",
//                            "FAN",
//                            "FIG",
//                            "NET",
//                            "GET"
//                        )
//                    )
//                )
//            },
//            {
//                assertEquals(
//                    listOf(
//                        "AA",
//                        "AB"
//                    ),
//                    SquareCrossword.buildCrossword(
//                        2,
//                        listOf(
//                            "AA",
//                            "AB",
//                            "AB",
//                            "AA"
//                        )
//                    )
//                )
//            },
            {
                val n = 20
                val matrix = generateMatrix(n)
                println("matrix: \n${matrix.toListString().joinToString("\n")}")
                println("words: \n${parseWordsFromMatrix(n, matrix).joinToString("\n")}")
                assertEquals(
                    matrix.toListString(),
                    SquareCrossword.buildCrossword(
                        n,
                        parseWordsFromMatrix(n, matrix)
                    )
                )
            },
        )
    }

    private val wordsAlphabet = 'A'..'Z'
    private fun generateMatrix(n: Int): Array<CharArray> = Array(n) {
        CharArray(n) {
            wordsAlphabet.random()
        }
    }

    private fun Array<CharArray>.toListString(): List<String>{
        val sb = StringBuilder()
        val n = size
        return (0 until n).map { i ->
            (0 until n).forEach { j ->
                sb.append(this[i][j])
            }
            sb.toString().also {
                sb.clear()
            }
        }
    }

    private fun parseWordsFromMatrix(n: Int, matrix: Array<CharArray>): List<String> {
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

//SOBL
//NGOO
//IUBD
//LGUB
//TODD
//INIG
//GBLD
//ILST
//
//SOBL x NGOO (2/3, 2/4)
//SOBL x IUBD (3/3)
//SOBL x LGUB (3/4)
//SOBL x TODD (2/2)
//SOBL x INIG ()
//SOBL x GBLD (3/2, 4/3)
//SOBL x ILST (4/2, 1/3)

package ru.smirnov.educational.ya2021

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import ru.smirnov.educational.DataAmount
import ru.smirnov.educational.Utils
import ru.smirnov.educational.Utils.filReaderFromClasspath
import ru.smirnov.educational.Utils.parseList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Duration
import java.util.concurrent.atomic.AtomicInteger
import kotlin.random.Random

internal class ETreeGameTest {

    @Test
    fun genTree() {
        val q = generateTree(3000, 4, 0.2)
        println(q.first)
        println(q.second)
        println("" + q.first.length + " - " + q.second.size)
    }

    //todo write more tests
    @Test
    fun process() {
        Utils.assertTimeLimitAndMemoryUsageLimit(
            Duration.ofSeconds(2),
            DataAmount.ofMega(256),
            listOf(
                {
                    assertEquals(
                        ETreeGame.Winner.First,
                        ETreeGame.process(
                            2, 0, ".+",
                            listOf(
                                1 to 2
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.First,
                        ETreeGame.process(
                            2, 0, ".-",
                            listOf(
                                1 to 2
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.Draw,
                        ETreeGame.process(
                            2, 0, ".0",
                            listOf(
                                1 to 2
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.Second,
                        ETreeGame.process(
                            2, 0, "..+-",
                            listOf(
                                1 to 2,
                                2 to 3,
                                2 to 4
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.First,
                        ETreeGame.process(
                            8, 1, "..-0..-+",
                            listOf(
                                1 to 2,
                                2 to 3,
                                2 to 4,
                                1 to 5,
                                5 to 6,
                                6 to 7,
                                6 to 8
                            )
                        )
                    )
                },
                {
                    assertEquals(
                        ETreeGame.Winner.Draw,
                        ETreeGame.process(
                            8, 1, "..-0..0+",
                            listOf(
                                1 to 2,
                                2 to 3,
                                2 to 4,
                                1 to 5,
                                5 to 6,
                                6 to 7,
                                6 to 8
                            )
                        )
                    )
                },
                {
                    val file = filReaderFromClasspath("ya2021/ETreeGame/6334_entry.txt")
                    val points = file.readLine()
                    val relations = file.readLine()
                        assertEquals(
                        ETreeGame.Winner.Draw,
                        ETreeGame.process(
                            6334, 5,
                            points,
                            parseList(relations, "), (") {
                                it.split(", ").run{get(0).toInt() to get(1).toInt()}
                            }
                        )
                    )
                },
            )
        )
    }

    private fun generateTree(minN: Int, maxWidth: Int, leafChance: Double): Pair<String, List<Pair<Int, Int>>> {
        val treeSB = StringBuilder()
        val treeRelations = mutableListOf<Pair<Int, Int>>()
        val n = AtomicInteger(0)

        treeSB.append('.')
        var prevLayerIndices = mutableListOf(n.incrementAndGet())
        while (prevLayerIndices.isNotEmpty()) {
            val layerIndices = mutableListOf<Int>()
            if (treeSB.length < minN) {
                prevLayerIndices.forEach { prev ->
                    val width = Random.nextInt(1, maxWidth + 1)
                    (0 until width).forEach {
                        val isLeaf = Random.nextDouble(1.0) <= leafChance
                        if (isLeaf) {
                            val leafState = Random.nextInt(-1, 2)
                            if (leafState == 1) treeSB.append('+')
                            else if (leafState == -1) treeSB.append('-')
                            else if (leafState == 0) treeSB.append('0')
                            else error("illegal state")
                            n.incrementAndGet().apply {
                                treeRelations.add(prev to this)
                            }
                        } else {
                            treeSB.append('.')
                            n.incrementAndGet().apply {
                                treeRelations.add(prev to this)
                                layerIndices.add(this)
                            }
                        }
                    }
                }
            } else {
                prevLayerIndices.forEach { prev ->
                    val leafState = Random.nextInt(-1, 2)
                    if (leafState == 1) treeSB.append('+')
                    else if (leafState == -1) treeSB.append('-')
                    else if (leafState == 0) treeSB.append('0')
                    else error("illegal state")
                    n.incrementAndGet().apply {
                        treeRelations.add(prev to this)
                    }
                }
            }

            prevLayerIndices = layerIndices
        }

        return treeSB.toString() to treeRelations
    }
}
package ru.smirnov.educational.ya2022

import java.util.*

object L1DfsBfs {

    fun process(lists: Array<CharArray>): Int {
        val processedPeaks = mutableSetOf<Pair<Int, Int>>() //можно обойтись без сета если заменять клетки массива с 1 на 0 после прочтения
        val m = lists.size
        val n = lists.first().size
        var islandCounter = 0

        for (i in 0 until m) {
            for (j in 0 until n) {
                if (!processedPeaks.contains(i to j)) { //если эту точку еще не обходили
                    if(bfsBuildGraph(lists, processedPeaks, m, n, i, j)){
                        ++islandCounter
                    }
                }
            }
        }

        return islandCounter
    }

    fun bfsBuildGraph(lists: Array<CharArray>, processedPeaks: MutableSet<Pair<Int, Int>>, m: Int, n: Int, iStart: Int, jStart: Int): Boolean {
        val queue: Queue<Pair<Int, Int>> = LinkedList()
        queue.add(iStart to jStart)
        var isIsland = false

        while (queue.isNotEmpty()) {
            val (i, j) = queue.poll()
            if (i < 0 || i >= m || j < 0 || j >= n) {
                //skip
            } else if (processedPeaks.add(i to j)) {
                if (lists[i][j] == '1') {
                    isIsland = true
                    queue.add(i+1 to j)
                    queue.add(i to j+1)
                    queue.add(i-1 to j)
                    queue.add(i to j-1)
                }
            }
        }

        return isIsland
    }

}
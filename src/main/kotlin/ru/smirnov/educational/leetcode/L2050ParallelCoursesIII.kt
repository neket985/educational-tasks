package ru.smirnov.educational.leetcode

object L2050ParallelCoursesIII {

    fun minimumTime(n: Int, relations: Array<IntArray>, time: IntArray): Int = getMaxExecTime(relations, time, n)

    fun getMaxExecTime(relations: Array<IntArray>, times: IntArray, n: Int): Int {
        val relationsNextByPreviousMap = relations.groupBy({ it[0] }, { it[1] })
        val relationsPreviousByNextMap = relations.groupBy({ it[1] }, { it[0] })

        //находим все точки, от которых идет начало графа
        val startGraphsPos = relationsNextByPreviousMap.filter { !relationsPreviousByNextMap.containsKey(it.key) }

        val maxSumPathMap = mutableMapOf<Int, Int>()
        var totalMaxSum = 0
        val list = iterator<Set<Int>> {
            var layerSet = startGraphsPos.keys
            while (layerSet.isNotEmpty()) {
                yield(layerSet)
                layerSet = layerSet.flatMap {
                    relationsNextByPreviousMap[it]?.filter {
                        maxSumPathMap.keys.containsAll(
                            relationsPreviousByNextMap[it] ?: emptyList()
                        )
                    } ?: emptyList()
                }.toSet()
            }
        }
        list.forEach { layerSet ->
            layerSet.forEach { point ->
                val maxSum = times[point - 1] +
                        (relationsPreviousByNextMap[point]?.maxOfOrNull { maxSumPathMap[it] ?: 0 } ?: 0)
                if (totalMaxSum < maxSum) totalMaxSum = maxSum
                maxSumPathMap[point] = maxSum
            }
        }

        return maxOf(totalMaxSum, times.maxOrNull() ?: 0)
    }

}
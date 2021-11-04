package ru.smirnov.educational.leetcode

import java.util.*

object L2050ParallelCoursesIII {

    fun minimumTime(n: Int, relations: Array<IntArray>, time: IntArray): Int = getMaxExecTime(relations, time, n)

    fun getMaxExecTime(relations: Array<IntArray>, times: IntArray, n: Int): Int {
        val enteredItems = mutableSetOf<Int>()
        val relationsNextByPreviousMap = relations.groupBy({ it[0] }, { it[1] })

        var max = 0
        (1..n).forEach { index ->
            if (!enteredItems.contains(index)) {
                maxExecTimeForIndexRecurcive(index, times, relationsNextByPreviousMap, enteredItems).apply {
                    if (max < this) {
                        max = this
                    }
                }
            }
        }
        return max
    }

    fun maxExecTimeForIndexRecurcive(
        index: Int,
        times: IntArray,
        relationsNextByPreviousMap: Map<Int, List<Int>>,
        enteredItems: MutableSet<Int>
    ): Int =
        times[index - 1] + run {
            var max = 0
            relationsNextByPreviousMap[index]?.forEach {
                maxExecTimeForIndexRecurcive(it, times, relationsNextByPreviousMap, enteredItems).apply {
                    if (max < this) {
                        max = this
                    }
                }
            }
            max
        }.apply {
            enteredItems.add(index)
        }

    fun maxExecTimeForIndexWhile(
        index: Int,
        times: IntArray,
        relationsNextByPreviousMap: Map<Int, List<Int>>,
        enteredItems: MutableSet<Int>
    ): Int {
        var ci = index
        var ctime = times[ci - 1]
        var max = 0
        val stack = Stack<Pair<Int, Int>>()
        while (true) {
            relationsNextByPreviousMap[ci]?.forEach {
                stack.push(it to ctime + times[it - 1])
            } ?: run {
                //one of branch is fulled
                if (max < ctime) {
                    max = ctime
                }
                ctime = 0
            }
            enteredItems.add(ci)
            if (stack.isEmpty()) {
                break
            } else {
                stack.pop().apply {
                    ci = this.first
                    ctime = this.second
                }
            }
        }

        return max
    }
}
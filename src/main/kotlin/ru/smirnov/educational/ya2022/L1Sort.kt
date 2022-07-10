package ru.smirnov.educational.ya2022

object L1Sort {

    fun merge(vararg intervals: IntArray): Array<IntArray> {
        quickSort(intervals) { this.compareTo(it) }

        val mergedIntervals = mutableListOf<IntArray>()
        var lastInterval: IntArray = intervals.first()
        for (i in intervals) {
            val (start, end) = i
            val lastEnd = lastInterval[1]
            if (start > lastEnd) {
                mergedIntervals.add(lastInterval)
                lastInterval = i
                continue
            }

            //needs to merge
            if (end > lastEnd) {
                lastInterval[1] = end
            } else {
                continue
            }
        }
        mergedIntervals.add(lastInterval)

        return mergedIntervals.toTypedArray()
    }

    fun IntArray.compareTo(other: IntArray): Int =
        if (this[0] > other[0]) 1
        else if (this[0] < other[0]) -1
        else if (this[1] > other[1]) 1
        else if (this[1] < other[1]) -1
        else 0


    //pivot element - first item
    fun <T> quickSort(items: Array<T>, fromIdx: Int = 0, toIdx: Int = items.lastIndex, compareTo: T.(T) -> Int) {
        if (toIdx - fromIdx <= 0) return
        val pivotElement = items[fromIdx]

        var lowestIdx = fromIdx + 1
        for (i in fromIdx + 1..toIdx) {
            val element = items[i]
            val compares = element.compareTo(pivotElement)

            if (compares < 0) {
                //move smaller element to left
                swap(items, i, lowestIdx++)
            }
        }
        //move pivot element to center
        swap(items, fromIdx, --lowestIdx)

        //left
        quickSort(items, fromIdx, lowestIdx - 1, compareTo)
        //right
        quickSort(items, lowestIdx + 1, toIdx, compareTo)
    }

    fun <T> swap(items: Array<T>, i: Int, j: Int) {
        val element = items[i]
        items[i] = items[j]
        items[j] = element
    }


    fun mergeSort(items: Array<IntArray>): Array<IntArray> {
        return items
    }
}
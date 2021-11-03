package ru.smirnov.educational.ya2021

object BTechCredit {

    fun main() {
        val (N, X, K) = readLine()!!.split(" ").map { it.toInt() }
        val arr = readLine()!!.split(" ").map { it.toLong() }
        println(process(N, X, K, arr))
    }

    fun process(N: Int, X: Int, K: Int, arr: List<Long>): Long {
        val reminderMap = mutableMapOf<Long, Long>()

        val partsSizesMap = mutableMapOf<Long, Long>()
        val partsStartIndex = mutableListOf<Pair<Long, Long>>()
        var sumPartsSize = 0L
        arr.toSortedSet().forEach { num ->
            val reminder = num % X
            val part = num / X

            if (reminderMap.containsKey(reminder)) return@forEach
            reminderMap[reminder] = part

            partsSizesMap[part] = ++sumPartsSize

            if (partsStartIndex.isEmpty()) {
                partsStartIndex.add(part to 0)
            } else {
                val (lastPart, lastSI) = partsStartIndex.last()
                if (lastPart != part) {
                    partsStartIndex.add(part to partsSizesMap[lastPart]!! * (part - lastPart) + lastSI)
                }
            }
        }

        val kMinOne = K-1
        val (nearPart, nearPartSI) = partsStartIndex.findNearestLeftSide(kMinOne.toLong())
        val kMinusNearSI = (kMinOne - nearPartSI)
        val kPart = kMinusNearSI / partsSizesMap[nearPart]!! + nearPart
        val kIndex = kMinusNearSI.toInt() % partsSizesMap[nearPart]!!.toInt()
        val reminderSet = reminderMap.toList().filter { it.second <= nearPart }.map { it.first }.sorted()

        val result = X * kPart + reminderSet[kIndex]
        return result
    }

    tailrec fun List<Pair<Long, Long>>.findNearestLeftSide(K: Long, from: Int = 0, to: Int = this.size - 1): Pair<Long, Long> {
        val size = to - from + 1
        val index =
            if (size == 1) {
                return this.elementAt(from)
            } else if (size > 1) {
                (size / 2 - 1) + from
            } else {
                throw IllegalStateException("illegal")
            }
        val left = this.elementAt(index)
        val right = this.elementAt(index + 1)
        if (left.second == K) return left
        if (right.second == K) return right
        if (left.second < K && right.second > K) return left
        if (left.second < K && right.second < K) return findNearestLeftSide(K, index + 1, to)
        if (left.second > K && right.second > K) return findNearestLeftSide(K, from, index)
        else throw IllegalStateException("else statement is unbelievable")
    }


    fun processNotOptimize(N: Int, X: Int, K: Int, arr: List<Long>): Long {
        val set = arr.toSortedSet()

        var i = 0
        val last = set.last()
        //полный сет
        while (set.last() == last && i < set.size) {
            set.add(set.elementAt(i) + X)
            ++i
        }
        val kKef = K / set.size
        val kIndex = K % set.size
        val res = if (kIndex == 0) {
            set.last() + set.size * (kKef - 1)
        } else {
            set.elementAt(kIndex - 1) + set.size * kKef
        }
        return res
    }
}
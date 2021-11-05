package ru.smirnov.educational.ya2021

object BTechCredit {

    @JvmStatic
    fun main(args: Array<String>) {
        val (N, X, K) = readLine()!!.split(" ").map { it.toInt() }
        val arr = readLine()!!.split(" ").map { it.toLong() }
        println(process(N, X, K, arr))
    }

    //X - период повторяемости
    //K - целевой индекс в полной последовательности
    //arr - массив элементов, задающих начало последовательностей с периодом X
    fun process(N: Int, X: Int, K: Int, arr: List<Long>): Long {
        //key - остаток от деления на X (уникальное смещение, для удаления дублирующихся последовательностей)
        //value - целочисленное деление на X (первая партиция, с которой начинается последовательность, относящаяся к данному смещению)
        val reminderMap = mutableMapOf<Long, Long>()

        //размеры последовательностей на определенной партиции
        val partsSizesMap = mutableMapOf<Long, Long>()
        //начальные индексы в полной последовательности для определенной партиции
        val partsStartIndex = mutableListOf<Pair<Long, Long>>()
        //общее число уникальных последовательностей
        var sumPartsSize = 0L
        arr.toSortedSet().forEach { num ->
            val reminder = num % X
            val part = num / X

            if (reminderMap.containsKey(reminder)) return@forEach
            reminderMap[reminder] = part
            partsSizesMap[part] = ++sumPartsSize

            //для первой партиции начальный индекс = 0
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
        //находим ближайшую слева к нашему К партицию. в рамках этой партиции будем высчитывать конкретное число в последовательности в зависимости от оставшегося пути до К и размера партиции
        val (nearPart, nearPartSI) = partsStartIndex.findNearestLeftSide(kMinOne.toLong())
        //оставшийся путь до К от начала ближайшей партиции
        val kMinusNearSI = (kMinOne - nearPartSI)
        //число целых партиций, которые мы пройдем до К
        val kPart = kMinusNearSI / partsSizesMap[nearPart]!! + nearPart
        //число оставшихся шагов, в рамках последней партиции
        val kIndex = kMinusNearSI.toInt() % partsSizesMap[nearPart]!!.toInt()
        //сет, состоящий из элементов (уникальных смещений), входящих в нашу партицию (некоторые элементы нашей полной последовательности могут еще не входить в данную партицию из-за того, что стартовая партиция у них больше текущей)
        val reminderSet = reminderMap.toList().filter { it.second <= nearPart }.map { it.first }.sorted()

        val result = X * kPart + reminderSet[kIndex]
        return result
    }

    //бинарный поиск ближайшей партиции слева
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
}
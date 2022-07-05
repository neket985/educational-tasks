package ru.smirnov.educational.ya2022

object L1HashTable {

    fun process2(vararg nums: Int): Int {
        val sorted = nums.sorted()
        var prev: Int = sorted.first()
        var prevCount = 1

        for (i in 1 until sorted.size) {
            val cur = sorted[i]
            if (prev != cur) {
                if (prevCount == 2) {
                    prev = cur
                    prevCount = 1
                } else {
                    return prev
                }
            } else {
                ++prevCount
            }
        }

        return prev
    }

    //лучшее решение с литкода
    fun process(vararg nums: Int): Int {
        var q = 0
        for (n in nums) {
            q = q xor n
        }
        return q
    }
}
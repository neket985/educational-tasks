package ru.smirnov.educational.ya2022

object L2HashTable {

    fun process2(vararg nums: Int, target: Int): IntArray {
        val map = nums.mapIndexed { i, v ->
            v to i
        }.groupBy({ it.first }, { it.second })

        for (idx in nums.indices) {
            val secondN = target - nums[idx]
            val secondIdx = map[secondN]?.firstOrNull { it != idx }
            if (secondIdx != null) {
                return intArrayOf(idx, secondIdx)
            }
        }

        error("unbelievable")
    }

    //best on leetcode
    fun process(vararg nums: Int, target: Int): IntArray {
        val map = mutableMapOf<Int, Int>()

        for (idx in nums.indices) {
            val num = nums[idx]
            val secondN = target - num
            if(map.containsKey(secondN)){
                return intArrayOf(map[secondN]!!, idx)
            }else{
                map.put(num, idx)
            }
        }

        error("unbelievable")
    }
}
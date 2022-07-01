package ru.smirnov.educational.leetcode.contest291


object III_DivisibleElementsSubarrays {

    fun countDistinct(vararg nums: Int, k: Int, p: Int): Int {
        val subarraysSet = mutableSetOf<String>()
        var tailIndex = 0
        var headIndex = 0
        var dividedElementsCount = 0

        val lastIndex = nums.size - 1

        while (headIndex <= lastIndex) {
            while (tailIndex < headIndex) {
                ++tailIndex
                if (nums[tailIndex - 1] % p == 0) {
                    --dividedElementsCount
                    break
                }
            }

            while (headIndex <= lastIndex) {
                if (nums[headIndex] % p == 0) {
                    if (dividedElementsCount >= k) break
                    ++dividedElementsCount
                }
                ++headIndex
            }

            subarraysSet.addAll(subarrays(*nums.copyOfRange(tailIndex, headIndex)))
        }

        return subarraysSet.size
    }

    fun subarrays(vararg arr: Int): Set<String> {
        val arrSize = arr.size

        val set = (arr.indices).reversed().flatMap { len ->
            (0 until arrSize - len).map { offset ->
                arr.copyOfRange(offset, len + offset + 1).joinToString(",")
            }
        }.toSet()

        return set
    }

}
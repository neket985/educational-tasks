package ru.smirnov.educational.ya2022

object L1BinarySearch {

    fun process(vararg nums: Int, target: Int): Int {
        if (nums.size == 0) return -1

        var headIdx = nums.lastIndex
        var tailIdx = 0
        while (true) {
            val checkLength = headIdx - tailIdx
            if (checkLength <= 2) {
                for (i in tailIdx..headIdx) {
                    if (nums[i] == target) return i
                }
                return -1
            }

            val checkIdx = tailIdx + (checkLength / 2)
            val checkElement = nums[checkIdx]

            if (checkElement == target) return checkIdx
            else if (checkElement > target) {
                headIdx = checkIdx
            } else {
                tailIdx = checkIdx
            }
        }

    }

}
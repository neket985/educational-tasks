package ru.smirnov.educational.tinkoff

object Test {
    /**
     * Найти максимальное число повторяющихся элементов среди двух массивов
     */

    fun findMaxCountDuplicatedElements(arr1: List<Int>, arr2: List<Int>): Int {
        val map = mutableMapOf<Int, Int>() //number to count
        for(i in arr1){
            map[i] = map.computeIfAbsent(i){ 0 } + 1
        }
        for(i in arr2){
            map[i] = map.computeIfAbsent(i){ 0 } + 1
        }

        return map.values.maxOrNull() ?: 0
    }


    @Deprecated("Это решение я придумал на собесе, но оно слишком сложное и не работает полностью")
    private fun findMaxCountDuplicatedElementsOhSheet(arr1: List<Int>, arr2: List<Int>): Int {
        if(arr1.isEmpty()) return getMaxCountDuplicatesForSingleList(arr2.iterator())
        if(arr2.isEmpty()) return getMaxCountDuplicatesForSingleList(arr1.iterator())

        val iter1 = arr1.iterator()
        val iter2 = arr2.iterator()

        var value1 = getNextOrMinusTwo(iter1)
        var value2 = getNextOrMinusTwo(iter2)

        var maxDuplicatesCount = 0
        var duplicatesCount = 0
        var lastCompareValue = -1

        while (true) {
            if (lastCompareValue != -1) {
                if (value1 == lastCompareValue) {
                    ++duplicatesCount
                    value1 = getNextOrMinusTwo(iter1)
                } else if (value2 == lastCompareValue) {
                    ++duplicatesCount
                    value2 = getNextOrMinusTwo(iter2)
                } else {
                    lastCompareValue = -1
                    if (maxDuplicatesCount < duplicatesCount) {
                        maxDuplicatesCount = duplicatesCount
                    }
                    duplicatesCount = 0
                }
            } else if (value1 == -2) {
                val maxForOther = getMaxCountDuplicatesForSingleList(iter2)
                if(maxDuplicatesCount< maxForOther){
                    maxDuplicatesCount = maxForOther
                }
                break
            } else if (value2 == -2) {
                val maxForOther = getMaxCountDuplicatesForSingleList(iter1)
                if(maxDuplicatesCount< maxForOther){
                    maxDuplicatesCount = maxForOther
                }
                break
            } else {
                if (value1 == value2) {
                    lastCompareValue = value1
                } else if (value1 < value2) {
                    value1 = getNextOrMinusTwo(iter1)
                } else if (value2 < value1) {
                    value2 = getNextOrMinusTwo(iter2)
                }
            }
        }

        return maxDuplicatesCount
    }

    fun getMaxCountDuplicatesForSingleList(iter: Iterator<Int>): Int{
        var maxDuplicatesCount = 0
        var duplicatesCounter = 1
        var lastCheckValue = -1

        while (iter.hasNext()){
            val value = iter.next()
            if(value==lastCheckValue){
                ++duplicatesCounter
            }else{
                lastCheckValue = value
                if(maxDuplicatesCount<duplicatesCounter){
                    maxDuplicatesCount = duplicatesCounter
                }
                duplicatesCounter=1
            }
        }

        return maxDuplicatesCount
    }

    fun getNextOrMinusTwo(iter: Iterator<Int>): Int {
        if (!iter.hasNext()) return -2
        return iter.next()
    }

}
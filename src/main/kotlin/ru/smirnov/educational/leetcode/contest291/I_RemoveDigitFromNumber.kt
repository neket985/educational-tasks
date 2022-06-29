package ru.smirnov.educational.leetcode.contest291

object I_RemoveDigitFromNumber {

    fun maxNumberAfterRemove(number: String, digit: Char): String{
        var maxNumberDigitForRemoveIndex = 0
        val digitAsByte = digit.toString().toByte()

        for (i in number.indices) {
            if(number[i]==digit){
                maxNumberDigitForRemoveIndex = i

                val nextDigit = number.getOrNull(i+1)?.toString()?.toByte() ?: break
                if(nextDigit > digitAsByte){
                    break //завершаем выполнение досрочно, так как заменяемая цифра меньше заменяющей => бОльшего числа мы уже не добьемся
                }
            }
        }

        val maxNumber = number.removeRange(maxNumberDigitForRemoveIndex, maxNumberDigitForRemoveIndex+1)
        return maxNumber
    }


}
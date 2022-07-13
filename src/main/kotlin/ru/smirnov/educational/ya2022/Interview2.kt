package ru.smirnov.educational.ya2022

import java.util.*

object Interview2 {

    // Дана строка (возможно, пустая), состоящая из букв A-Z и пробелов, разделяющих слова.
// Нужно написать функцию, которая развернет слова.
// И сгенерирует ошибку, если на вход пришла невалидная строка.
// Примеры:
// "QUICK FOX  JUMPS"->"KCIUQ XOF  SPMUJ"


    // "" -> ""
// "a " -> exception
// "TEST QWE" -> "TSET EWQ"
// "QUICK FOX  JUMPS"->"KCIUQ XOF  SPMUJ"
// "A" -> "A"
    fun reverseWords(input: String): String {
        val stack = Stack<Char>()
        val spaceChar = ' '
        val allowedCharRange = 'A'..'Z'

        val resultSb = StringBuilder("")

        for (ch in input) {
            if (allowedCharRange.contains(ch)) {
                stack.add(ch)
            } else if (ch == spaceChar) {
                while (stack.isNotEmpty()) {
                    val stackChar = stack.pop()
                    resultSb.append(stackChar)
                }
                resultSb.append(" ")
            } else {
                throw RuntimeException("symbol is not valid $ch")
            }
        }

        while (stack.isNotEmpty()) {
            val stackChar = stack.pop()
            resultSb.append(stackChar)
        }

        return resultSb.toString()
    }


// Дан массив целых чисел, нужно найти непустой подотрезок
// (непрерывную подпоследовательность) с заданной суммой X, либо сказать, что это невозможно.
// Для найденного отрезка (если он существует) следует выдать на выход индексы его концов.
// [1, 2, 4, 5, 6] X = 6 -> (1, 2)
// [] X = 6 -> (-1, -1)
// [1, 2] X = 6 -> (-1, -1)
// [1, 3, 4] X = 4 -> (0, 1)  4 = 4 - 0
// [1, 3, 4] X = 3 -> (1, 1)  3 = 4 - 1

// S_ij = S_i - S_j
// x = S_i - S_j
// S_j = S_i - x

// S_i - S_i-1 == a_i

// [1, 2, 4]  X = -6 // i = 5 j = 3

//    fun findSubArray(input: Array<Int>, x: Int): Pair<Int, Int>{
//        val partlySum = Array(input.size) //[1, 3, 7]
//        val sIMinusXMap = mutableMapOf<Int, Int>() //sum - x to idx
//        //(7, 0)
//        //(9, 1)
//        //(13, 2)
//
//        var sum = 0
//        for(i in input.indices){
//            val elementI = input[i]
//
//            sum += elementI
//            partlySum[i] = sum
//            val sIMinusX = sum - x
//            if(sIMinusX==0){
//                return 0 to i
//            }
//            sIMinusXMap.put(sIMinusX to i)
//        }
//
//        for(j in input.indices){
//            val elementJ = input[j]
//
//            val i = sIMinusXMap[elementJ]
//            if(i != null){
//                if(i<=j) return i to j
//                else return j to i
//            }
//        }
//
//        return -1 to -1
//    }


    fun findSubArray(vararg input: Int, x: Int): Pair<Int, Int> {
        val sIMinusXMap = mutableMapOf<Int, Int>() //sum - x to idx

        var sum = 0
        val partlySum = Array(input.size) { i ->
            val elementI = input[i]

            sum += elementI
            val sIMinusX = sum - x
            if (sIMinusX == 0) {
                return 0 to i
            }
            sIMinusXMap.put(sIMinusX, i)
            sum
        } //[1, 3, 7]

        for (j in input.indices) {
            val elementJ = partlySum[j]

            val i = sIMinusXMap[elementJ]
            if (i != null) {
                return j+1 to i
            }
        }

        return -1 to -1
    }

}
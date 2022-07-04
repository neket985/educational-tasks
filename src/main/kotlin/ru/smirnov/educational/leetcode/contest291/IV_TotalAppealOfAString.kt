package ru.smirnov.educational.leetcode.contest291

import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue


object IV_TotalAppealOfAString {

    //todo надо уменьшить время выполнения
    @OptIn(ExperimentalTime::class)
    fun appealsCount(s: String): Long {
        val subs =
            measureTimedValue {
                substrings(s)
            }.apply {
                println("subs exec time: " + this.duration)
            }.value
        val result =
            measureTimedValue {
                subs.map {
                    if (it.length == 1) 1
                    else it.toSet().count()
                }.sum()
            }.apply {
                println("toSet exec time: " + this.duration)
            }.value

        return result.toLong()
    }

    fun substrings(s: String): List<String> {
        val size = s.length

        return (0 until size).reversed().flatMap { len ->
            (0 until size - len).map { offset ->
                s.substring(offset, len + offset + 1)
            }
        }
    }

}
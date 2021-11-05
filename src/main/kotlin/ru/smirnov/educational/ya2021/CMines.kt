package ru.smirnov.educational.ya2021

import java.util.*

object CMines {
    fun process(N: Int, M: Int, K: Int, mines: List<Pair<Int, Int>>): Int {
        var countFilledSpaces = 0
        val checkedSpaces = mutableListOf<Pair<Int, Int>>()
        (1..M).forEach { m ->
            (1..N).forEach nLoop@{ n ->
                val pair = n to m
                //skip if mine or always checked
                if (mines.contains(pair) || checkedSpaces.contains(pair)) return@nLoop
                //check all filled space
                stackedCheckSpaces(pair, N, M, mines, checkedSpaces)
                ++countFilledSpaces
            }
        }

        return countFilledSpaces
    }

    fun stackedCheckSpaces(
        pos: Pair<Int, Int>,
        N: Int,
        M: Int,
        mines: List<Pair<Int, Int>>,
        checked: MutableList<Pair<Int, Int>>
    ) {
        val stack = Stack<Pair<Int, Int>>()
        stack.push(pos)
        while(!stack.isEmpty()) {
            val forCheck = stack.pop()
            //skip if mine or always checked or out of map
            if (mines.contains(forCheck) || checked.contains(forCheck) || forCheck.first < 1 || forCheck.second < 1 || forCheck.first > N || forCheck.second > M) continue
            checked.add(forCheck)
            stack.push(forCheck.first to forCheck.second - 1)
            stack.push(forCheck.first to forCheck.second + 1)
            stack.push(forCheck.first - 1 to forCheck.second)
            stack.push(forCheck.first + 1 to forCheck.second)
        }
    }

}
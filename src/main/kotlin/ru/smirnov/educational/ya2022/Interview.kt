package ru.smirnov.educational.ya2022

import java.util.*

object Interview {

    //дана строка. надо определить, что скобочки в ней проставлены корректно (все открытые закрыты, закрытых без открытых нет)
    fun test1(str: String): Boolean {
        val stack: Stack<Char> = Stack()
        val braces = mapOf('(' to ')', '[' to ']', '{' to '}')

        for (char in str) {
            if (braces.keys.contains(char)) {
                stack.add(char)
            } else if (braces.values.contains(char)) {
                if (stack.isEmpty()) return false

                val braceForClose = stack.pop()
                if (char != braces[braceForClose]) {
                    return false
                }
            }
        }

        return stack.isEmpty()
    }

    data class Node(val value:Int, val left:Node?, val right:Node?)


    //является ли дерево бинарным деревом поиска
    fun isBST(node: Node): Boolean{
        val (isValid, _, _) = getMinAndMax(node)
        return isValid
    }

    fun getMinAndMax(node: Node): Triple<Boolean, Int, Int>{
        var leftIsValid = true
        var rightIsValid = true
        var mainMin = node.value
        var mainMax = node.value

        node.left?.let{
            val (isValid, min, max) = getMinAndMax(it)
            if(!isValid || max >= node.value){
                leftIsValid = false
            }
            if(max > mainMax){
                mainMax = max
            }
            if(min < mainMin){
                mainMin = min
            }
        }

        node.right?.let{
            val (isValid, min, max) = getMinAndMax(it)
            if(!isValid || min <= node.value){
                rightIsValid = false
            }
            if(max > mainMax){
                mainMax = max
            }
            if(min < mainMin){
                mainMin = min
            }
        }

        return Triple(leftIsValid && rightIsValid, mainMin, mainMax)
    }
}
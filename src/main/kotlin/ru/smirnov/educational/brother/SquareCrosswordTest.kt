package ru.smirnov.educational.brother


object SquareCrosswordTest {

    @JvmStatic
    fun main(args: Array<String>) {
        val n = readLine()!!.toInt()
        val input = (0 until 2 * n).map {
            readLine()!!.also {
                if(it.length!=n) throw IllegalStateException("wrong input string length: [$it]")
            }
        }
        val result = SquareCrossword.buildCrossword(n, input)
        println(result.joinToString("\n"))
    }


}

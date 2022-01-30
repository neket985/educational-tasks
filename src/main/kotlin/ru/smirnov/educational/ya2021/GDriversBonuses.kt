package ru.smirnov.educational.ya2021

import ru.smirnov.educational.ya2021.GDriversBonuses.DriverQueue.Companion.mapNextWhile
import ru.smirnov.educational.ya2021.GDriversBonuses.DriverQueue.Companion.mapPrevWhile
import kotlin.math.max

object GDriversBonuses {

    fun process(N: Int, raitings: List<Int>): Long {
        val minPoints = mutableListOf<DriverQueue>()

        //mark queue
        var prev: DriverQueue? = null
        raitings.forEach {
            prev = DriverQueue(it, prev)
        }

        var next: DriverQueue? = null
        mapPrevWhile(prev, {
            it!!.next = next
            it.buildType()

            if (it.type == DriverType.minPoint) minPoints.add(it)

            next = it
        }, { it != null })

        //все рейтинги равны
        if (minPoints.isEmpty()) return raitings.size * 500L

        //размечаем рейтинги
        minPoints.forEach {
            //определяем направление движения
            if (it.nextRaitingOrSame() > it.raiting) { //вправо
                mapNextWhile(it, {
                    it!!.buildPayScore(true)
                }, { it!!.type != DriverType.maxPoint }, true)
            }
            if (it.prevRaitingOrSame() > it.raiting) { //влево
                mapPrevWhile(it, {
                    it!!.buildPayScore(false)
                }, { it!!.type != DriverType.maxPoint }, true)
            }
        }

        //проходимся по очереди и суммируем счет
        var summ = 0L
        mapNextWhile(next!!, {
            summ += it!!.score
        }, { it != null })

        return summ * 500
    }


    data class DriverQueue(
        val raiting: Int,
        val previous: DriverQueue?,
        var next: DriverQueue? = null,
        var type: DriverType? = null,
        var score: Long = 0
    ) {
        fun nextRaitingOrSame() = next?.raiting ?: raiting
        fun prevRaitingOrSame() = previous?.raiting ?: raiting

        fun buildType() {
            type =
                if (raiting == prevRaitingOrSame() && raiting == nextRaitingOrSame()) {
                    score = 1
                    DriverType.equalityPoint
                } else if (raiting > prevRaitingOrSame() && raiting < nextRaitingOrSame() ||
                    raiting < prevRaitingOrSame() && raiting > nextRaitingOrSame()
                ) {
                    DriverType.point
                } else if (raiting >= prevRaitingOrSame() && raiting >= nextRaitingOrSame()) {
                    DriverType.maxPoint
                } else if (raiting <= prevRaitingOrSame() && raiting <= nextRaitingOrSame()) {
                    DriverType.minPoint
                } else {
                    error("it's wrong state!")
                }
        }

        fun buildPayScore(nextDirection: Boolean) {
            score =
                when (type) {
                    DriverType.minPoint, DriverType.equalityPoint -> 1
                    DriverType.maxPoint -> {
                        if (nextDirection) max((previous?.score ?: -1) + 1, score)
                        else max((next?.score ?: -1) + 1, score)
                    }
                    DriverType.point -> {
                        if (nextDirection) previous!!.score + 1
                        else next!!.score + 1
                    }
                    else -> error("wrong state!")
                }
        }

        override fun toString(): String {
            return "DriverQueue(raiting=$raiting, type=$type, score=$score)"
        }

        companion object {
            tailrec fun mapNextWhile(first: DriverQueue?, map: (DriverQueue?) -> Unit, whileCond: (DriverQueue?) -> Boolean, isDoWhile: Boolean = false) {
                if (isDoWhile) {
                    map(first)
                    if (whileCond(first)) {
                        mapNextWhile(first?.next, map, whileCond, isDoWhile)
                    }
                } else {
                    if (whileCond(first)) {
                        map(first)
                        mapNextWhile(first?.next, map, whileCond, isDoWhile)
                    }
                }
            }

            tailrec fun mapPrevWhile(first: DriverQueue?, map: (DriverQueue?) -> Unit, whileCond: (DriverQueue?) -> Boolean, isDoWhile: Boolean = false) {
                if (isDoWhile) {
                    map(first)
                    if (whileCond(first)) {
                        mapPrevWhile(first?.previous, map, whileCond, isDoWhile)
                    }
                } else {
                    if (whileCond(first)) {
                        map(first)
                        mapPrevWhile(first?.previous, map, whileCond, isDoWhile)
                    }
                }
            }
        }
    }

    enum class DriverType {
        minPoint, maxPoint, equalityPoint, point;
    }
}
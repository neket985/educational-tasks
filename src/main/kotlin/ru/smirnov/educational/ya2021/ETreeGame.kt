package ru.smirnov.educational.ya2021

object ETreeGame {

    //если к четное, то реверсом управляет первый игрок. если нечетное, то второй
    //игрок стремится к
    //          своим выигрышным,
    //          своим проигрышным, если он управляет реверсом и глубина до листа позволяет его выполнить,
    //          проигрышным для противника
    //          ничейным
    //листьям, глубина пути до которых четная

    //обратный поиск в ширину
    fun process(n: Int, k: Int, pointStates: String, relations: List<Pair<Int, Int>>): Winner {
        val pointsStatesMap = pointStates.mapIndexed { index, c -> index to LeafState.valueOf(c) }.toMap()
        val root = TreeUnit(null, null, null, 0, mutableListOf(), null)
        val treeUnitsGroupedByDeep =
            recursiveTreeFillRelations(root, 1, 1, relations.groupBy({ it.first }, { it.second }), pointsStatesMap)
                .groupBy { it.deep }

        (0..treeUnitsGroupedByDeep.keys.maxOrNull()!!).reversed().forEach { deep ->
            val isFirstPlayerStep = deep % 2 == 0
            val reverseMayCalls = deep >= k
            treeUnitsGroupedByDeep[deep]!!.forEach { unit ->
                val isLeaf = unit.state != null

                if (isLeaf) {
                    //при подсчете победителя на листе учитывается не тот, кто ходит сейчас, а тот, кто ходил до этого
                    val isFirstPlayerMoves = !isFirstPlayerStep
                    val previousPlayerWinsState = if (isFirstPlayerMoves) Winner.First else Winner.Second
                    unit.win = when (unit.state!!) {
                        LeafState.D -> {
                            Winner.Draw
                        }
                        LeafState.W -> {
                            previousPlayerWinsState
                        }
                        LeafState.L -> {
                            previousPlayerWinsState.reverse()
                        }
                    }
                    if (reverseMayCalls) unit.reverseWin = unit.win!!.reverse()
                } else {
                    val currentPlayerWinsState = if (isFirstPlayerStep) Winner.First else Winner.Second
                    val currentPlayerControlsReverse = k % 2 == deep % 2

                    unit.findWinnerPath(currentPlayerWinsState)
                    if (reverseMayCalls) {
                        if (currentPlayerControlsReverse) {
                            //win OR reverseWin
                            unit.findReverseWinnerPathControlled(currentPlayerWinsState)
                            if (deep == k) {
                                //choose win or reverseWin path
                                val finalWinState =
                                    if (unit.win == currentPlayerWinsState || unit.reverseWin == currentPlayerWinsState) {
                                        currentPlayerWinsState
                                    } else if (unit.win == Winner.Draw || unit.reverseWin == Winner.Draw) {
                                        Winner.Draw
                                    } else unit.win
                                unit.win = finalWinState
                            }
                        } else {
                            //win AND reverseWin
                            unit.findReverseWinnerPathUncontrolled(currentPlayerWinsState)
                        }
                    }
                }
            }
        }

        return root.win!!
    }

    private fun recursiveTreeFillRelations(
        root: TreeUnit,
        rootNum: Int,
        deep: Int,
        relations: Map<Int, List<Int>>,
        pointsStatesMap: Map<Int, LeafState?>
    ): List<TreeUnit> {
        val list = mutableListOf(root)
        root.next = relations[rootNum]?.map {
            TreeUnit(pointsStatesMap[it - 1], null, null, deep, emptyList(), root).apply {
                list.addAll(recursiveTreeFillRelations(this, it, deep + 1, relations, pointsStatesMap))
            }
        } ?: emptyList()
        return list
    }

    data class TreeUnit(
        var state: LeafState?,
        var win: Winner?,
        var reverseWin: Winner?,
        val deep: Int,
        var next: List<TreeUnit>,
        val prev: TreeUnit?
    ) {
        override fun toString(): String = "TreeUnit(state=$state, deep=$deep, win=$win, reverse=$reverseWin)"

        fun findWinnerPath(preferState: Winner) {
            if (this.next.any { it.win!! == preferState }) this.win = preferState
            else if (this.next.any { it.win!! == Winner.Draw }) this.win = Winner.Draw
            else this.win = preferState.reverse()
        }

        fun findReverseWinnerPathControlled(preferState: Winner) {
            if (this.next.any { it.reverseWin!! == preferState }) this.reverseWin = preferState
            else if (this.next.any { it.reverseWin!! == Winner.Draw }) this.reverseWin = Winner.Draw
            else this.reverseWin = preferState.reverse()
        }

        fun findReverseWinnerPathUncontrolled(preferState: Winner) {
            if (this.next.any { it.win!! == preferState } && this.next.any { it.reverseWin!! == preferState })
                this.reverseWin = preferState
            else if (this.next.any { it.reverseWin!! == Winner.Draw }) this.reverseWin = Winner.Draw //todo think about it
            else this.reverseWin = preferState.reverse()
        }
    }

    enum class LeafState() {
        W, L, D;

        companion object {
            fun valueOf(c: Char) = when (c) {
                '0' -> D
                '+' -> W
                '-' -> L
                else -> null
            }
        }

        fun reverse() = when (this) {
            W -> L
            L -> W
            D -> D
        }
    }

    enum class Winner() {
        First, Second, Draw;

        fun reverse() = when (this) {
            First -> Second
            Second -> First
            Draw -> Draw
        }
    }
}
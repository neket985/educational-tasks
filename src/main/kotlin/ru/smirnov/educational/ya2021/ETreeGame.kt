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
        val root = TreeUnit(null, 0, mutableListOf(), null)
        val treeUnitsGroupedByDeep = recursiveTreeFillRelations(root, 1, 1, relations.groupBy({ it.first }, { it.second }), pointsStatesMap)
            .groupBy { it.deep }

        (0 .. treeUnitsGroupedByDeep.keys.maxOrNull()!!).reversed().forEach { deep ->
            val units = treeUnitsGroupedByDeep[deep]!!
            //todo search
        }


    }

    private fun recursiveTreeFillRelations(root: TreeUnit, rootNum: Int, deep: Int, relations: Map<Int, List<Int>>, pointsStatesMap: Map<Int, LeafState?>): List<TreeUnit>{
        val list = mutableListOf(root)
        root.next = relations[rootNum]?.map {
            TreeUnit(pointsStatesMap[it - 1], deep, mutableListOf(), root).apply {
                list.addAll(recursiveTreeFillRelations(this, it, deep+1, relations, pointsStatesMap))
            }
        } ?: emptyList()
        return list
    }

    data class TreeUnit(
        var state: LeafState?,
        val deep: Int,
        var next: List<TreeUnit>,
        val prev: TreeUnit?
    )

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
    }
}
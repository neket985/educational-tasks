package ru.smirnov.educational.ya2022

object L1LinkedList {

    fun process(lists: Array<ListNode?>): ListNode? {
        val filteredLists = lists.filterNotNull()
        if (filteredLists.isEmpty()) return null

        val map = mutableMapOf<Int, Int>() //key to count
        val sortedSet = sortedSetOf<Int>()
        filteredLists.forEach {
            var cur: ListNode? = it
            while (cur != null) {
                map.compute(cur.`val`) { k, ov ->
                    (ov ?: 0) + 1
                }
                sortedSet.add(cur.`val`)

                cur = cur.next
            }
        }

        val result = sortedSet.flatMap { num ->
            List(map[num]!!) { num }
        }

        return ListNode.valueOf(*result.toIntArray())
    }

    fun parse(s: String): Array<ListNode?> {
        return s.substring(2, s.length - 2).split("],[").map {
            ListNode.valueOf(*it.split(",").map { it.toInt() }.toIntArray())
        }.toTypedArray()
    }

    class ListNode(var `val`: Int) {
        var next: ListNode? = null

        companion object {
            fun valueOf(vararg nums: Int): ListNode? {
                if (nums.isEmpty()) return null

                val first = ListNode(nums.first())
                var cur = first
                for (i in 1 until nums.size) {
                    val new = ListNode(nums[i])
                    cur.next = new
                    cur = new
                }

                return first
            }
        }

        override fun equals(other: Any?): Boolean {
            if (other is ListNode) {
                return this.`val` == other.`val` && this.next == other.next
            } else {
                return super.equals(other)
            }
        }
    }
}
package ru.smirnov.educational

import ru.smirnov.educational.Utils.formatBytes


class DataAmount(amount: Long, unit: DataUnit) {
    val bytes: Long = amount * Math.pow(1024.0, unit.exp.toDouble()).toLong()

    override fun toString(): String = formatBytes(bytes)

    companion object{
        fun ofKilo(amount: Long) = DataAmount(amount, DataUnit.KB)
        fun ofMega(amount: Long) = DataAmount(amount, DataUnit.MB)
        fun ofGiga(amount: Long) = DataAmount(amount, DataUnit.GB)
        fun ofTera(amount: Long) = DataAmount(amount, DataUnit.TB)
        fun ofPeta(amount: Long) = DataAmount(amount, DataUnit.PB)
    }
}

enum class DataUnit(val exp: Int) {
    KB(1),
    MB(2),
    GB(3),
    TB(4),
    PB(5),
    EB(6);
}
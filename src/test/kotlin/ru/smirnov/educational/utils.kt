package ru.smirnov.educational

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.function.ThrowingSupplier
import org.junit.platform.commons.logging.LoggerFactory
import java.time.Duration

object Utils {
    private val logger = LoggerFactory.getLogger(this::class.java)
    fun formatBytes(bytes: Long): String {
        if (bytes == 1L || bytes == -1L) {
            return "$bytes byte"
        }
        return if (bytes < 1024 && bytes > -1024) {
            "$bytes bytes"
        } else {
            val exp = (Math.log(Math.abs(bytes).toDouble()) / Math.log(1024.0)).toInt()
            val unitPrefix = "kMGTPE"[exp - 1]
            return String.format("%.1f %cB", bytes / Math.pow(1024.0, exp.toDouble()), unitPrefix)
        }
    }

    fun assertTimeLimitAndMemoryUsageLimit(
        timeout: Duration,
        maxProcessUsage: DataAmount,
        processes: List<() -> Unit>
    ) {
        val runtime = Runtime.getRuntime()
        val totalMemoryBeforeStart = runtime.totalMemory()
        logger.info { "Total memory usage before start ${formatBytes(totalMemoryBeforeStart)}" }
        processes.forEach { process ->
            Assertions.assertTimeoutPreemptively(timeout, ThrowingSupplier {
                val startUsage = runtime.totalMemory() - runtime.freeMemory()
                process.invoke()
                val processUsage = totalMemoryBeforeStart + runtime.totalMemory() - runtime.freeMemory() - startUsage
                logger.info { "Process memory usage ${formatBytes(processUsage)}" }
                assert(processUsage < maxProcessUsage.bytes) {
                    "Memory usage limit. used ${formatBytes(processUsage)}, max $maxProcessUsage"
                }
            }, "Timeout $timeout")
        }
    }

    fun <T> parseList(ps: String, delimiter: String = ",", parse: (String) -> T) = ps.split(delimiter).map {
        parse(it)
    }
}
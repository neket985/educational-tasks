package ru.smirnov.educational.common

import me.tongfei.progressbar.InteractiveConsoleProgressBarConsumer
import java.io.PrintStream

class CustomConsoleProgressBarConsumer(out: PrintStream) : InteractiveConsoleProgressBarConsumer(out) {
    override fun close() {

    }

}
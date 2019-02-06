package ru.hse.cli.lines

import ru.hse.cli.Context
import java.io.PrintStream

class Assignment(private val variable: String, private val value: String): Line() {
    override fun run(context: Context, output: PrintStream): Boolean {
        context.vars[variable] = value
        return false
    }
}
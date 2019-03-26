package ru.hse.cli.lines

import ru.hse.cli.Context
import java.io.PrintStream

/**
 * Assignment line in CLI.
 * @param variable filename of the variable
 * @param value variable's new value
 */
class Assignment(private val variable: String, private val value: String): Line() {
    override fun run(context: Context, output: PrintStream): Boolean {
        context.vars[variable] = value
        return false
    }
}
package ru.hse.cli.lines

import ru.hse.cli.Context
import java.io.PrintStream

/**
 *An algebraic data type representing an entered line: either an assignment or a command pipeline.
 */
abstract class Line {

    /**
     * Executes the line in a given context.
     * @param context execution context
     * @param output PrintStream to write to
     */
    abstract fun run(context: Context, output: PrintStream): Boolean
}
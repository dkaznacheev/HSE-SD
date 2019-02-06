package ru.hse.cli.lines

import ru.hse.cli.Context
import java.io.PrintStream

//An algebraic data type representing an entered line: either an assignment or a command pipeline.
abstract class Line {
    abstract fun run(context: Context, output: PrintStream): Boolean
}
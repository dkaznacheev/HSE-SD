package ru.hse.cli

import ru.hse.cli.commands.CliCommand

//An algebraic data type representing an entered line: either an assignment or a command pipeline.
sealed class Line {
    data class Assignment(val variable: String, val value: String): Line()
    data class Pipeline(val commands: List<CliCommand>): Line()
}
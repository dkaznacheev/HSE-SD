package ru.hse.cli

import ru.hse.cli.commands.CliCommand

sealed class Line {
    data class Assignment(val variable: String, val value: String): Line()
    data class Pipeline(val commands: List<CliCommand>): Line()
}
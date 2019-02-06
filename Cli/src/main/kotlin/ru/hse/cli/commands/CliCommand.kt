package ru.hse.cli.commands

import ru.hse.cli.Context

// CLI command parent class
abstract class CliCommand(protected val args: List<String>) {
    abstract fun execute(input: String? = null, context: Context): String
    abstract fun getName(): String
}
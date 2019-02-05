package ru.hse.cli.commands

// CLI command parent class
abstract class CliCommand(protected val args: List<String>) {
    abstract fun execute(input: String? = null): String
    abstract fun getName(): String
}
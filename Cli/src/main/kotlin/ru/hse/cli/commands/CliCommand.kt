package ru.hse.cli.commands

abstract class CliCommand(protected val args: List<String>) {
    abstract fun execute(input: String? = null): String
    abstract fun getName(): String
}
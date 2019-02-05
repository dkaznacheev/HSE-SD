package ru.hse.cli.commands

abstract class CliCommand(protected val args: List<String>, protected val input: String?) {
    abstract fun execute(): String
    abstract fun getName(): String
}
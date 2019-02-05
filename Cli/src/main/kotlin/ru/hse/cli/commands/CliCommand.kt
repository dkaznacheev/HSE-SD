package ru.hse.cli.commands

abstract class CliCommand (val name: String) {
    abstract fun execute(args: List<String>): String
}
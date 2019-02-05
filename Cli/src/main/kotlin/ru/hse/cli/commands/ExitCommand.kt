package ru.hse.cli.commands

class ExitCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "exit"

    override fun execute(input: String?): String {
        return ""
    }

    companion object {
        fun createExitCommand(args: List<String>) = ExitCommand(args)
    }
}
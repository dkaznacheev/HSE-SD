package ru.hse.cli.commands

//exit command, closes the command line if called
class ExitCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "exit"

    override fun execute(input: String?): String {
        return ""
    }

    companion object {
        fun createExitCommand(args: List<String>) = ExitCommand(args)
    }
}
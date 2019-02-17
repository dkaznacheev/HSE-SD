package ru.hse.cli.commands

import ru.hse.cli.Context

// pwd command prints current working directory
class PwdCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "pwd"

    override fun execute(input: String?, context: Context): String {
        return System.getProperty("user.dir")
    }

    companion object {
        fun createPwdCommand(args: List<String>) = PwdCommand(args)
    }
}

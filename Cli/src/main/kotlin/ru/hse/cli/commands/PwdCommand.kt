package ru.hse.cli.commands

import ru.hse.cli.Context

class PwdCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "pwd"

    override fun execute(input: String?, context: Context): String {
        println("!")
        return context.currentDir
    }

    companion object {
        fun createPwdCommand(args: List<String>) = PwdCommand(args)
    }
}
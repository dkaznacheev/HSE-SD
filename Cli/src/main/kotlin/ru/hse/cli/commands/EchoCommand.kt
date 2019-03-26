package ru.hse.cli.commands

import ru.hse.cli.Context

/**
 * echo writes all its argument to output.
 * @property args list of arguments
 */
class EchoCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "echo"

    override fun execute(input: String?, context: Context): String {
        return args.joinToString(" ")
    }

    companion object {
        fun createEchoCommand(args: List<String>) = EchoCommand(args)
    }
}
package ru.hse.cli.commands

import ru.hse.cli.Context

/**
 * echo writes all its argument to output.
 * @property args list of arguments
 */
class EchoCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = "echo"

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        return args.joinToString(" ")
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createEchoCommand(args: List<String>) = EchoCommand(args)
    }
}
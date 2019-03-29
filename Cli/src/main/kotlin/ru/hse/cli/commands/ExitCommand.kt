package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.parser.exceptions.ExitException

/**
 * exit command closes the command line if called.
 * @property args list of arguments
 */
class ExitCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = "exit"

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        throw ExitException()
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createExitCommand(args: List<String>) = ExitCommand(args)
    }
}
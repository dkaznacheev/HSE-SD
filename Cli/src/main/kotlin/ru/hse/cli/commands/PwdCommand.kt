package ru.hse.cli.commands

import ru.hse.cli.Context

/**
 * pwd prints current working directory.
 * @property args list of arguments
 */
class PwdCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = "pwd"

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        return context.currentDir
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createPwdCommand(args: List<String>) = PwdCommand(args)
    }
}
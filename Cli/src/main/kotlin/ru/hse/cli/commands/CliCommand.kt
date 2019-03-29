package ru.hse.cli.commands

import ru.hse.cli.Context

/**
 * CLI command parent class.
 * @property args list of arguments
 */
abstract class CliCommand(protected val args: List<String>) {
    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    abstract fun execute(input: String? = null, context: Context): String

    /**
     * Gets the command's name.
     */
    abstract fun getName(): String
}
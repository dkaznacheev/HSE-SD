package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.util.FileReadService.readFiles
import java.util.*

/**
 * wc command counts lines, words and bytes of input or a file from arguments if there was no input.
 * @property args list of arguments
 */
class WcCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = "wc"

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        val wcInput = input ?: readFiles(getName(), args)
        val newlines = wcInput.split(Regex("(\r\n)|\n|\r")).size
        val words = StringTokenizer(wcInput).countTokens()
        val bytes = wcInput.toByteArray().size
        return  "$newlines".padStart(8) +
                "$words".padStart(8) +
                "$bytes".padStart(8)
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createWcCommand(args: List<String>) = WcCommand(args)
    }
}
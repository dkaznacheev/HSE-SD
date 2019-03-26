package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.commands.factory.CliCommandFactory

/**
 * wc command counts lines, words and bytes of input or a file from arguments if there was no input.
 * @property args list of arguments
 */
class WcCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "wc"

    private fun loadFiles(context: Context): String {
        return CliCommandFactory.createCliCommand("cat", args).execute(context = context)
    }

    override fun execute(input: String?, context: Context): String {
        val wcInput = input ?: loadFiles(context)
        val newlines = wcInput.count { it == '\n' } + 1
        val words = wcInput.replace('\n', ' ').split(' ').size
        val bytes = wcInput.toByteArray().size
        return  "$newlines".padStart(8) +
                "$words".padStart(8) +
                "$bytes".padStart(8)
    }

    companion object {
        fun createWcCommand(args: List<String>) = WcCommand(args)
    }
}
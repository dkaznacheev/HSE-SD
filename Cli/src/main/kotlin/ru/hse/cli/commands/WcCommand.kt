package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.commands.Util.loadFiles

// wc command counts lines, words and bytes of input or a file from arguments if there was no input.
class WcCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "wc"

    override fun execute(input: String?, context: Context): String {
        val wcInput = input ?: loadFiles(args, context)
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
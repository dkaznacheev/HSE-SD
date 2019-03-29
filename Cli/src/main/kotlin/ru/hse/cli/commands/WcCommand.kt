package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.util.FileReadService.readFiles
import ru.hse.cli.util.StringUtils.getLines
import java.util.*

/**
 * wc command counts lines, words and bytes of input or a file from arguments if there was no input.
 * @property args list of arguments
 */
class WcCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "wc"

    override fun execute(input: String?, context: Context): String {
        val wcInput = input ?: readFiles(getName(), args)
        val newlines = getLines(wcInput).size
        val words = StringTokenizer(wcInput).countTokens()
        val bytes = wcInput.toByteArray().size
        return  "$newlines".padStart(8) +
                "$words".padStart(8) +
                "$bytes".padStart(8)
    }

    companion object {
        fun createWcCommand(args: List<String>) = WcCommand(args)
    }
}
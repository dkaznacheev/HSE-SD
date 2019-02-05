package ru.hse.cli.commands

import ru.hse.cli.commands.factory.CliCommandFactory


class WcCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "wc"

    private fun loadFiles(): String {
        return CliCommandFactory.createCliCommand("cat", args).execute()
    }

    override fun execute(input: String?): String {
        val wcInput = input ?: loadFiles()
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
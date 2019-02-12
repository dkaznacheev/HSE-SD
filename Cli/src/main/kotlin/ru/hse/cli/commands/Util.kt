package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.commands.factory.CliCommandFactory

object Util {
    fun loadFiles(filenames: List<String>, context: Context): String {
        return CliCommandFactory.createCliCommand("cat", filenames).execute(context = context)
    }
}
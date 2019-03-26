package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.util.FileReadService.readFiles

/**
 * cat concatenates all files from arguments or redirects input to output if there are no arguments.
 * @property args list of arguments
  */
class CatCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "cat"

    override fun execute(input: String?, context: Context): String {
        return if (args.isNotEmpty())
            readFiles(getName(), args)
        else
            input ?: ""
    }

    companion object {
        fun createCatCommand(args: List<String>) = CatCommand(args)
    }
}
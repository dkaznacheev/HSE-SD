package ru.hse.cli.commands

import ru.hse.cli.Context
import ru.hse.cli.util.FileReadService.readFiles

/**
 * cat concatenates all files from arguments or redirects input to output if there are no arguments.
 * @property args list of arguments
  */
class CatCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = "cat"

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        return if (args.isNotEmpty())
            readFiles(getName(), args)
        else
            input ?: ""
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createCatCommand(args: List<String>) = CatCommand(args)
    }
}
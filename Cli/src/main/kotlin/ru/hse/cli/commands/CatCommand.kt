package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.File

/**
 * cat concatenates all files from arguments or redirects input to output if there are no arguments.
 * @property args list of arguments
  */
class CatCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "cat"

    override fun execute(input: String?, context: Context): String {
        if (args.isNotEmpty())
            return args.joinToString(System.lineSeparator()) { name ->
                val file = File(name)
                when {
                    file.isFile -> return@joinToString file.readLines().joinToString(System.lineSeparator())
                    file.isDirectory -> return@joinToString "cat: $name/: is a directory"
                    else -> return@joinToString "cat: $name: no such file or directory"
                }
            }
        else
            return input ?: ""
    }

    companion object {
        fun createCatCommand(args: List<String>) = CatCommand(args)
    }
}
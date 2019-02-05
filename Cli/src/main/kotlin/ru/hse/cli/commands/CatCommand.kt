package ru.hse.cli.commands

import java.io.File

// cat concatenates all files from arguments or redirects input to output if there are no arguments
class CatCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "cat"

    override fun execute(input: String?): String {
        if (args.isNotEmpty())
            return args.joinToString("\n") { name ->
                val file = File(name)
                when {
                    file.isFile -> return@joinToString file.readLines().joinToString("\n")
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
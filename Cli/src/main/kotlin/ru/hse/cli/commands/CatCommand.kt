package ru.hse.cli.commands

import java.io.File

class CatCommand private constructor(args: List<String>, input: String?) : CliCommand(args, input) {
    override fun getName() = "cat"

    override fun execute(): String {
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
        fun createCatCommand(args: List<String>, input: String?) = CatCommand(args, input)
    }
}
package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.File

// ls command shows files inside current working directory
class LsCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "ls"

    override fun execute(input: String?, context: Context): String {
        var cwd = File(System.getProperty("user.dir"))
        if (args.isNotEmpty())
            cwd = File(args[0])

        return if (cwd.exists())
            cwd.listFiles().sorted().joinToString("\n") {
                file -> file.name
            }
        else
            "ls: ${cwd.name}: No such file or directory"
    }

    companion object {
        fun createLsCommand(args: List<String>) = LsCommand(args)
    }
}
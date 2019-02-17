package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.File

/**
 * `ls` command shows files inside current working directory.
 * @param args list of arguments passed to the command
 */
class LsCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "ls"

    /**
     * Executes `ls` command with specified arguments.
     * @return list of files (each on a separate line) inside specified directory or
     * an error message if directory does not exist; if no directory was passed current
     * working directory is used instead
     */
    override fun execute(input: String?, context: Context): String {
        var cwd = File(System.getProperty("user.dir"))
        if (args.isNotEmpty())
            cwd = File(args[0])

        return if (cwd.exists())
            cwd.listFiles().sorted().joinToString(System.lineSeparator()) {
                file -> file.name
            }
        else
            "ls: ${cwd.name}: No such file or directory"
    }

    companion object {
        fun createLsCommand(args: List<String>) = LsCommand(args)
    }
}
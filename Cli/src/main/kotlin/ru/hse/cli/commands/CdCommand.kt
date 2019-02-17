package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.File

// cd command changes current working directory to the specified one
class CdCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "cd"

    override fun execute(input: String?, context: Context): String {
        if (args.isNotEmpty()) {
            val canonicalPath = File(args[0]).canonicalPath
            if (File(canonicalPath).exists()) {
                System.setProperty("user.dir", canonicalPath)
            } else {
                return "cd: ${args[0]}: No such file or directory"
            }
        } else {
            System.setProperty("user.dir", System.getProperty("user.home"))
        }
        return ""
    }

    companion object {
        fun createCdCommand(args: List<String>) = CdCommand(args)
    }
}

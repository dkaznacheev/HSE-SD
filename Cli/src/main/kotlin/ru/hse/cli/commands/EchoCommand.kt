package ru.hse.cli.commands

class EchoCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = "echo"

    override fun execute(input: String?): String {
        return args.joinToString(" ")
    }

    companion object {
        fun createEchoCommand(args: List<String>) = EchoCommand(args)
    }
}
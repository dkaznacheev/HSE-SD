package ru.hse.cli.commands

class EchoCommand private constructor(args: List<String>, input: String?) : CliCommand(args, input) {
    override fun getName() = "echo"

    override fun execute(): String {
        return args.joinToString(" ")
    }

    companion object {
        fun createEchoCommand(args: List<String>, input: String?) = EchoCommand(args, input)
    }
}
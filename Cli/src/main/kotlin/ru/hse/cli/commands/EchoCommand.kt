package ru.hse.cli.commands

class EchoCommand : CliCommand("echo") {
    override fun execute(args: List<String>): String {
        return args.joinToString(" ")
    }
}
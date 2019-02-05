package ru.hse.cli.commands

class ExternalCommand private constructor(args: List<String>, input: String?) : CliCommand(args, input) {
    override fun getName() = ""

    override fun execute(): String {
        return ""
    }

    companion object {
        fun createExternalCommand(args: List<String>, input: String?) = ExternalCommand(args, input)
    }
}
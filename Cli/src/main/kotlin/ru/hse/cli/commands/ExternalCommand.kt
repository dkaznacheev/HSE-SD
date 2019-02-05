package ru.hse.cli.commands

class ExternalCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = ""

    override fun execute(input: String?): String {
        return ""
    }

    companion object {
        fun createExternalCommand(args: List<String>) = ExternalCommand(args)
    }
}
package ru.hse.cli.commands

import ru.hse.cli.commands.CatCommand.Companion.createCatCommand
import ru.hse.cli.commands.EchoCommand.Companion.createEchoCommand
import ru.hse.cli.commands.ExternalCommand.Companion.createExternalCommand

object CliCommandFactory {
    private val commandConstructors = mapOf<String, (List<String>, String?)-> CliCommand>(
        "cat" to ::createCatCommand,
        "echo" to ::createEchoCommand
    )

    fun createCliCommand(name: String, args: List<String>, input: String?): CliCommand {
        return commandConstructors.getOrDefault(name, ::createExternalCommand).invoke(args, input)
    }
}
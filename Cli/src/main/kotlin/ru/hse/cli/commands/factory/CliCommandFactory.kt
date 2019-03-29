package ru.hse.cli.commands.factory

import ru.hse.cli.commands.CatCommand.Companion.createCatCommand
import ru.hse.cli.commands.CliCommand
import ru.hse.cli.commands.EchoCommand.Companion.createEchoCommand
import ru.hse.cli.commands.ExitCommand.Companion.createExitCommand
import ru.hse.cli.commands.ExternalCommand.Companion.createExternalCommand
import ru.hse.cli.commands.PwdCommand.Companion.createPwdCommand
import ru.hse.cli.commands.WcCommand.Companion.createWcCommand

object CliCommandFactory {

    /**
     * Map from command names to commands' factory methods.
     */
    private val commandConstructors = mapOf<String, (List<String>)-> CliCommand>(
        "cat" to ::createCatCommand,
        "echo" to ::createEchoCommand,
        "exit" to ::createExitCommand,
        "wc" to ::createWcCommand,
        "pwd" to ::createPwdCommand
    )

    /**
     * Factory method for creating commands.
     */
    fun createCliCommand(name: String, args: List<String>): CliCommand {
        return if (name in commandConstructors)
            commandConstructors.getValue(name).invoke(args)
        else
            createExternalCommand(listOf(name) + args)
    }
}
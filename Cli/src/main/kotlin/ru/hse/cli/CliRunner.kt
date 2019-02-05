package ru.hse.cli

import ru.hse.cli.commands.CliCommand

class CliRunner {
    private val context = mutableMapOf<String, String>()

    fun runAssignment(variable: String, value: String) {
        context[variable] = value
    }

    fun runCommands(pipeline: List<CliCommand>) {
        if (pipeline.size == 1 && pipeline.first().getName() == "exit")
            return
        var buffer: String? = null
        for (command in pipeline) {
            buffer = command.execute(buffer)
        }
        println(buffer)
    }
}
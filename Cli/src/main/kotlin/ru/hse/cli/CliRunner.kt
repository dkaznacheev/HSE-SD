package ru.hse.cli

import ru.hse.cli.commands.CliCommand
import ru.hse.cli.parser.CliParser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream

class CliRunner(input: InputStream, private val output: PrintStream) {

    private val reader = BufferedReader(InputStreamReader(input))
    private val context = mutableMapOf<String, String>()
    private val parser = CliParser(context)

    private fun runAssignment(variable: String, value: String) {
        context[variable] = value
    }

    private fun runCommands(pipeline: List<CliCommand>) {
        /*if (pipeline.size == 1 && pipeline.first().getName() == "exit")
            return

        var buffer: String? = null
        for (command in pipeline) {
            buffer = command.execute(buffer)
        }

        output.println(buffer)
        */
        pipeline.forEach { println(it.getName()) }
    }

    fun run() {
        var line = reader.readLine()
        while (line != null) {
            val parsedLine = parser.parseLine(line)
            when(parsedLine) {
                is Line.Pipeline -> runCommands(parsedLine.commands)
                is Line.Assignment -> runAssignment(parsedLine.variable, parsedLine.value)
            }

            line = reader.readLine()
        }
    }
}

fun main() {
}
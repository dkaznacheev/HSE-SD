package ru.hse.cli

import ru.hse.cli.commands.CliCommand
import ru.hse.cli.parser.CliParser
import ru.hse.cli.parser.exceptions.EmptyCommandException
import ru.hse.cli.parser.exceptions.QuotesNotClosedException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream

class CliRunner(input: InputStream, private val output: PrintStream) {

    private val reader = BufferedReader(InputStreamReader(input))
    private val context = mutableMapOf<String, String>()
    private val parser = CliParser(context)

    /*
        Runs the assignment command.
     */
    private fun runAssignment(variable: String, value: String) {
        context[variable] = value
    }

    /*
        Runs the command pipeline, returns whether an exit command was called.
     */
    private fun runCommands(pipeline: List<CliCommand>): Boolean {
        if (pipeline.size == 1 && pipeline.first().getName() == "exit")
            return true

        var buffer: String? = null
        for (command in pipeline) {
            buffer = command.execute(buffer)
        }

        output.println(buffer)
        return false
    }

    /*
        Runs the command line cycle.
     */
    fun run() {
        var line = reader.readLine()
        while (line != null) {
            try {
                val parsedLine = parser.parseLine(line)
                when(parsedLine) {
                    is Line.Pipeline -> {
                        if(runCommands(parsedLine.commands))
                            return
                    }
                    is Line.Assignment -> runAssignment(parsedLine.variable, parsedLine.value)
                }
            } catch (e: EmptyCommandException) {
                output.println("Empty command in pipeline!")
            } catch (e: QuotesNotClosedException) {
                output.println("Quotes not closed!")
            }

            line = reader.readLine()
        }

    }

}

fun main() {
    CliRunner(System.`in`, System.out).run()
}
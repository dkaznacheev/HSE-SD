package ru.hse.cli

import ru.hse.cli.parser.CliParser
import ru.hse.cli.parser.exceptions.EmptyCommandException
import ru.hse.cli.parser.exceptions.QuotesNotClosedException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.PrintStream

/**
 * Class for running the command line interface.
 * @param input input stream for the cli runner
 * @param output output stream for the cli runner
 */
class CliRunner(input: InputStream, private val output: PrintStream) {

    private val reader = BufferedReader(InputStreamReader(input))
    private val context = Context(mutableMapOf(), System.getProperty("user.dir"))
    private val parser = CliParser(context)

    /**
     * Runs the command line cycle.
     */
    fun run() {
        var line = reader.readLine()
        while (line != null) {
            try {
                val parsedLine = parser.parseLine(line)
                if (parsedLine.run(context, output))
                    return
            } catch (e: EmptyCommandException) {
                output.println("Empty command in pipeline!")
            } catch (e: QuotesNotClosedException) {
                output.println("Quotes not closed!")
            }

            line = reader.readLine()
        }

    }
}
package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * External command call.
 * @property args list of arguments
 */
class ExternalCommand private constructor(args: List<String>) : CliCommand(args) {
    /**
     * Gets the command's name.
     */
    override fun getName() = ""

    /**
     * Executes the function with given input in given context.
     * @param input command input
     * @param context execution context
     */
    override fun execute(input: String?, context: Context): String {
        return try {
            val proc = ProcessBuilder(args)
                .redirectInput(ProcessBuilder.Redirect.PIPE)
                .redirectOutput(ProcessBuilder.Redirect.PIPE)
                .redirectError(ProcessBuilder.Redirect.PIPE)
                .start()
            if (input != null)
                proc.outputStream.write(input.toByteArray())
            proc.waitFor(60, TimeUnit.MINUTES)
            proc.inputStream.bufferedReader().readText()
        } catch(e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    companion object {
        /**
         * Factory method for creating this command.
         * @param args command arguments
         */
        fun createExternalCommand(args: List<String>) = ExternalCommand(args)
    }
}
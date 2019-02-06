package ru.hse.cli.commands

import ru.hse.cli.Context
import java.io.IOException
import java.util.concurrent.TimeUnit

//external command call
class ExternalCommand private constructor(args: List<String>) : CliCommand(args) {
    override fun getName() = ""

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
        fun createExternalCommand(args: List<String>) = ExternalCommand(args)
    }
}
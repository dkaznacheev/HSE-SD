package ru.hse.cli.lines

import ru.hse.cli.Context
import ru.hse.cli.commands.CliCommand
import java.io.PrintStream

/**
 * Pipeline is a sequence of commands where of a command get redirected to next command's input.
 * @param commands list of commands
 */
class Pipeline(private val commands: List<CliCommand>) : Line() {
    override fun run(context: Context, output: PrintStream): Boolean {
        if (commands.size == 1 && commands.first().getName() == "exit")
            return true

        var buffer: String? = null
        for (command in commands) {
            buffer = command.execute(buffer, context)
        }

        output.println(buffer)
        return false
    }
}
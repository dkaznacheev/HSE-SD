package ru.hse.cli.lines

import ru.hse.cli.Context
import ru.hse.cli.commands.CliCommand
import ru.hse.cli.parser.exceptions.ExitException
import ru.hse.cli.parser.exceptions.IsDirectoryException
import ru.hse.cli.parser.exceptions.NegativeLinesGrepException
import ru.hse.cli.parser.exceptions.NoSuchFileException
import java.io.PrintStream

/**
 * Pipeline is a sequence of commands where of a command get redirected to next command's input.
 * @param commands list of commands
 */
class Pipeline(private val commands: List<CliCommand>) : Line() {

    /**
     * Executes the line in a given context.
     * @param context execution context
     * @param output PrintStream to write to
     */
    override fun run(context: Context, output: PrintStream): Boolean {
        if (commands.size == 1 && commands.first().getName() == "exit")
            return true

        var buffer: String? = null
        for (command in commands) {
            try {
                buffer = command.execute(buffer, context)
            } catch (e: Throwable) {
                when (e) {
                    is NegativeLinesGrepException -> {
                        output.println("grep: can't have ${e.number} lines after match")
                        return false
                    }
                    is IsDirectoryException ->  {
                        output.println("${e.commandName}: ${e.filename}/: is a directory")
                        return false
                    }
                    is NoSuchFileException ->  {
                        output.println("${e.commandName}: ${e.filename}: no such file or directory")
                        return false
                    }
                    is ExitException -> {
                        return true
                    }
                }
            }
        }

        output.println(buffer)
        return false
    }
}
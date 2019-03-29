package ru.hse.cli.parser

import ru.hse.cli.Context
import ru.hse.cli.lines.Line
import ru.hse.cli.commands.CliCommand
import ru.hse.cli.commands.factory.CliCommandFactory
import ru.hse.cli.lines.Assignment
import ru.hse.cli.lines.Pipeline
import ru.hse.cli.parser.exceptions.EmptyCommandException
import ru.hse.cli.parser.exceptions.QuotesNotClosedException
import java.lang.StringBuilder

/**
 * Class for parsing lines from command line.
 * Uses vars for storing variables.
 * @param context execution context
 */
class CliParser(context: Context) {
    private val vars = context.vars

    /**
     * Expands unescaped variables in tokens.
     * @param token string token to expand
     */
    private fun expand(token: String): String {
        val result = StringBuilder()

        var escapedSingle = false
        var escapedDouble = false
        var startDollar = 0
        var inDollar = false
        var i = 0

        for (c in token) {
            when(c) {
                '\"' -> {
                    if (inDollar) {
                        val key = token.substring(startDollar, i)
                        result.append(vars.getOrDefault(key, ""))
                        inDollar = false
                    }

                    if (!escapedSingle) {
                        escapedDouble = !escapedDouble
                    } else {
                        result.append(c)
                    }
                }
                '\'' -> {
                    if (inDollar) {
                        val key = token.substring(startDollar, i)
                        result.append(vars.getOrDefault(key, ""))
                        inDollar = false
                    }

                    if (!escapedDouble) {
                        escapedSingle = !escapedSingle
                    } else {
                        result.append(c)
                    }
                }
                '\$' -> {
                    if (!escapedSingle) {
                        if (inDollar) {
                            val key = token.substring(startDollar, i)
                            result.append(vars.getOrDefault(key, ""))
                        }
                        inDollar = true
                        startDollar = i + 1
                    } else {
                        result.append(c)
                    }
                }
                in 'a'..'z', in 'A'..'Z', in '0'..'9' -> {
                    if (!inDollar)
                        result.append(c)
                }
                else -> {
                    if (inDollar) {
                        val key = token.substring(startDollar, i)
                        result.append(vars.getOrDefault(key, ""))
                        inDollar = false
                    }
                    result.append(c)
                }
            }
            i++
        }
        if (inDollar) {
            val key = token.substring(startDollar, token.length)
            result.append(vars.getOrDefault(key, ""))
        }
        if (escapedDouble or escapedSingle)
            throw QuotesNotClosedException()
        return result.toString()
    }

    /**
     * Checks whether the line is an assignment.
     * @param tokens a line to check
     */
    private fun isAssignment(tokens: List<String>): Boolean {
        if (tokens.size != 1)
            return false
        val parts = tokens.first().split('=')
        if (parts.size != 2)
            return false
        return true
    }

    /**
     * Splits line into tokens by unescaped pipe symbols.
     * Example: (echo "|" | cat) produces [echo "|", cat]
     * @param line string line to split
     */
    private fun splitByUnescaped(line: String): List<String> {
        var escapedSingle = false
        var escapedDouble = false

        val builder = StringBuilder()
        val result = mutableListOf<String>()

        for (c in line) {
            when(c) {
                '\"' -> {
                    if (!escapedSingle) {
                        escapedDouble = !escapedDouble
                    }
                    builder.append(c)
                }
                '\'' -> {
                    if (!escapedDouble) {
                        escapedSingle = !escapedSingle
                    }
                    builder.append(c)
                }
                '|' -> {
                    if (!escapedSingle && !escapedDouble) {
                        result.add(builder.toString())
                        builder.clear()
                    } else {
                        builder.append(c)
                    }
                }
                else -> builder.append(c)
            }
        }
        if (escapedDouble or escapedSingle)
            throw QuotesNotClosedException()
        result.add(builder.toString())
        return result
    }

    /**
     * Parses a command from a token.
     * @param input a token to parse
     */
    private fun parseCommand(input: String): CliCommand {
        if (input.isEmpty()) {
            throw EmptyCommandException()
        }
        val tokens = input.trim().split(' ')
        if (tokens.isEmpty())
            throw EmptyCommandException()
        val name = tokens.first()
        val args = tokens.drop(1)
        return CliCommandFactory.createCliCommand(name, args)
    }

    /**
     * Parses a token and returns either an assignment or a list of commands in a pipeline
     * @param line a token to parse
     */
    fun parseLine(line: String): Line {
        val tokens = splitByUnescaped(line)
            .map{ expand(it) }

        if (isAssignment(tokens)) {
            val parts = tokens.first().split('=')
            return Assignment(parts[0], parts[1])
        }

        return Pipeline(tokens.map{ parseCommand(it) })
    }
}
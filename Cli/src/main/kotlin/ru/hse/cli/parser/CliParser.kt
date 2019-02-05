package ru.hse.cli.parser

import ru.hse.cli.Line
import ru.hse.cli.commands.CliCommand
import ru.hse.cli.commands.factory.CliCommandFactory
import ru.hse.cli.parser.exceptions.EmptyCommandException
import ru.hse.cli.parser.exceptions.QuotesNotClosedException
import java.lang.StringBuilder

class CliParser(private val context: Map<String, String>) {

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
                        result.append(context.getOrDefault(key, ""))
                        inDollar = false
                    }

                    if (!escapedSingle) {
                        escapedDouble = !escapedDouble
                    } else {
                        result.append(c)
                    }
                }
                '\'' -> {
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
                            result.append(context.getOrDefault(key, ""))
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
                        result.append(context.getOrDefault(key, ""))
                        inDollar = false
                    }
                    result.append(c)
                }
            }
            i++
        }
        if (inDollar) {
            val key = token.substring(startDollar, token.length)
            result.append(context.getOrDefault(key, ""))
        }
        return result.toString()
    }

    private fun isAssignment(tokens: List<String>): Boolean {
        if (tokens.size != 1)
            return false
        val parts = tokens.first().split('=')
        if (parts.size != 2)
            return false
        return true
    }

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

    fun parseLine(line: String): Line {
        val tokens = splitByUnescaped(line)
            .map{ expand(it) }

        if (isAssignment(tokens)) {
            val parts = tokens.first().split('=')
            return Line.Assignment(parts[0], parts[1])
        }

        return Line.Pipeline(tokens.map{ parseCommand(it) })
    }

    private fun parseCommand(input: String): CliCommand {
        val tokens = input.trim().split(' ')
        if (tokens.isEmpty())
            throw EmptyCommandException()
        val name = tokens.first()
        val args = tokens.drop(1)
        return CliCommandFactory.createCliCommand(name, args)
    }

}
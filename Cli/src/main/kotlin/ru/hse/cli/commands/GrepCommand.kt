package ru.hse.cli.commands

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import ru.hse.cli.Context
import ru.hse.cli.commands.Util.loadFiles
import java.lang.Math.max

class GrepCommand private constructor(args: List<String>) : CliCommand(args) {

    override fun getName() = "grep"

    override fun execute(input: String?, context: Context): String {
        val options = Options()

        options.addOption("i", false, "case insensitive")
        options.addOption("w", false, "whole word search")
        options.addOption("A", true, "print n lines after match")
        options.addOption("h", false, "show help")

        val parser = DefaultParser()
        val cmd = parser.parse(options, args.toTypedArray())

        if (cmd.hasOption("h")) {
            return USAGE
        }

        val caseSensitive = !cmd.hasOption("i")
        val wholeWord = cmd.hasOption("w")
        val afterMatchLines = if (cmd.hasOption("A")) {
            cmd.getOptionValue("A").toIntOrNull() ?: 0
        } else 0

        val argList = cmd.argList
        if (argList.isEmpty())
            return USAGE

        val grepInput = if (argList.size > 1) {
            val filename = argList[1]
            loadFiles(listOf(filename), context)
        } else input ?: return USAGE

        var pattern = argList.first()
        if (wholeWord) {
            pattern = "(?<!\\p{L})$pattern(?!\\p{L})"
        }
        if (!caseSensitive) {
            pattern = "(?i)$pattern"
        }

        val result = pattern.toRegex().findAll(grepInput)

        if (afterMatchLines == 0) {
            return result.map { it.value }.joinToString("\n")
        }

        val linebreaks: List<Int> = grepInput
            .mapIndexedNotNull{ i, c -> if (c == '\n') i else null }.plus(grepInput.length - 1)

        return result
            .map { res ->
                val start = res.range.start
                val end = res.range.endInclusive
                val nextLineBreak = linebreaks
                    .filter { it > start }
                    .getOrElse(afterMatchLines) { linebreaks.last() }
                return@map grepInput.substring(start..max(nextLineBreak, end))
            }
            .joinToString("")
    }

    companion object {
        private const val USAGE = "usage: grep [OPTION]... PATTERN [FILE]... \n" +
                "-i: case insensitive\n" +
                "-w: whole word search\n" +
                "-A n: print n lines after match\n" +
                "-h: help\n"

        fun createGrepCommand(args: List<String>) = GrepCommand(args)
    }
}

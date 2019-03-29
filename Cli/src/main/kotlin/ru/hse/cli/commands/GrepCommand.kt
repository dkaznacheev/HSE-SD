package ru.hse.cli.commands

import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.Options
import ru.hse.cli.Context
import ru.hse.cli.commands.Util.loadFiles
import ru.hse.cli.util.StringUtils.getLines
import java.lang.Math.max
import java.util.*

class GrepCommand private constructor(args: List<String>) : CliCommand(args) {

    override fun getName() = "grep"

    override fun execute(input: String?, context: Context): String {
        val options = Options()
        val usageLine = USAGE.replace("\n", System.lineSeparator())
        options.addOption("i", false, "case insensitive")
        options.addOption("w", false, "whole word search")
        options.addOption("A", true, "print n lines after match")
        options.addOption("h", false, "show help")

        val parser = DefaultParser()
        val cmd = parser.parse(options, args.toTypedArray())

        if (cmd.hasOption("h")) {
            return usageLine
        }

        val caseSensitive = !cmd.hasOption("i")
        val wholeWord = cmd.hasOption("w")
        val afterMatchLines = if (cmd.hasOption("A")) {
            cmd.getOptionValue("A").toIntOrNull() ?: 0
        } else 0

        val argList = cmd.argList
        if (argList.isEmpty())
            return usageLine

        val grepInput = if (argList.size > 1) {
            val filename = argList[1]
            loadFiles(listOf(filename), context)
        } else input ?: return usageLine

        println(argList)
        var pattern = argList.first()

        if (wholeWord) {
            pattern = "\\b$pattern\\b"
        }
        if (!caseSensitive) {
            pattern = "(?i)$pattern"
        }

        val regex = pattern.toRegex()
        val inputLines = getLines(grepInput)
        val isMatched = Array(inputLines.size){ false }
        val result = StringJoiner(System.lineSeparator())

        for ((i, line) in inputLines.withIndex()) {
            if (regex.containsMatchIn(line)) {
                for (j in i..(i + afterMatchLines))
                    isMatched[j] = true
            }

            if (isMatched[i]) {
                result.add(line)
            }
        }

        return result.toString()
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

package ru.hse.cli.util

object StringUtils {
    fun getLines(input: String): List<String> {
        return input.split(Regex("(\r\n)|\n|\r"))
    }
}
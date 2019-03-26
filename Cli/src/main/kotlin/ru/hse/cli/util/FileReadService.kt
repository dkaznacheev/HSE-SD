package ru.hse.cli.util

import ru.hse.cli.parser.exceptions.IsDirectoryException
import ru.hse.cli.parser.exceptions.NoSuchFileException
import java.io.File

object FileReadService {
    fun readFiles(commandName: String = "", fileNames: List<String>): String {
        return fileNames.joinToString(System.lineSeparator()) { name ->
            val file = File(name)
            when {
                file.isFile -> return@joinToString file.readLines().joinToString(System.lineSeparator())
                file.isDirectory -> throw IsDirectoryException(name, commandName)
                else -> throw NoSuchFileException(name, commandName)
            }
        }
    }
}
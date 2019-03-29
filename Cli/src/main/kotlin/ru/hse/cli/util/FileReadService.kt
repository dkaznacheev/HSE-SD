package ru.hse.cli.util

import ru.hse.cli.parser.exceptions.IsDirectoryException
import ru.hse.cli.parser.exceptions.NoSuchFileException
import java.io.File

object FileReadService {

    /**
     * Reads files for a command and concatenates their content to a single string.
     * @param commandName name of a command invoking this
     * @param fileNames file names
     */
    fun readFiles(commandName: String = "", fileNames: List<String>): String {
        return fileNames.joinToString(System.lineSeparator()) { name ->
            val file = File(name)
            when {
                file.isFile -> return@joinToString file.readText()
                file.isDirectory -> throw IsDirectoryException(name, commandName)
                else -> throw NoSuchFileException(name, commandName)
            }
        }
    }
}
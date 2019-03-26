package ru.hse.cli.parser.exceptions

/**
 * An exception thrown when attempted to open the directory as file.
 */
data class IsDirectoryException(val filename: String, val commandName: String) : Throwable()

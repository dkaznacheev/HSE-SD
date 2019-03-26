package ru.hse.cli.parser.exceptions

/**
 * An exception thrown when attempted to open nonexistent file.
 */
data class NoSuchFileException(val filename: String, val commandName: String) : Throwable()
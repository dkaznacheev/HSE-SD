package ru.hse.cli

/**
 * Execution context.
 * @property vars system variables
 * @property currentDir directory context is currently in
 */
data class Context(val vars: MutableMap<String, String>,
                   var currentDir: String)
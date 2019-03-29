package ru.hse.cli

/**
 * Main object for jar building.
 */
object Main {

    /**
     * Program entry point.
     */
    @JvmStatic
    fun main(args: Array<String>) {
        CliRunner(System.`in`, System.out).run()
    }
}
package ru.hse.cli

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        CliRunner(System.`in`, System.out).run()
    }
}
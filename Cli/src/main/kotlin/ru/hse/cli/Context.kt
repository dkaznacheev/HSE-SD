package ru.hse.cli

data class Context(val vars: MutableMap<String, String>,
                   var currentDir: String)
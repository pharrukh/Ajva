package com.normuradov.ajva.utils

fun parseRecognizedText(text: String): List<String> {
    val parsedText = mutableListOf<String>()
    val lines = sanitize(text).split("\n")
    for (line in lines) {
        val words = line.split(" ")
        for (word in words) {
            if (word.length >= 4 && !word.matches(Regex("\\d+"))) {
                parsedText.add(word.lowercase())
            }
        }
    }

    return parsedText
}

private fun sanitize(input: String): String {
    val result = StringBuilder()
    for (char in input) {
        if (char.isLetterOrDigit() || char == '\'' || char == '\n' || char == ' ') {
            result.append(char)
        }
    }
    return result.toString()
}
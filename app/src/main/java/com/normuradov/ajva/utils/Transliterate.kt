package com.normuradov.ajva.utils

private val transliterationMap = mapOf(
    "o'" to "ў",
    "u" to "у",
    "z" to "з",
    "b" to "б",
    "e" to "е",
    " e" to " э",
    "k" to "к",
    "ch" to "ч",
    "a" to "а",
    "yo" to "ё",
    "yu" to "ю",
    "ya" to "я",
    "sh" to "ш",
    "zh" to "ж",
    "ts" to "ц",
    "f" to "ф",
    "g" to "г",
    "h" to "ҳ",
    "i" to "и",
    "j" to "ж",
    "l" to "л",
    "m" to "м",
    "n" to "н",
    "p" to "п",
    "r" to "р",
    "s" to "с",
    "t" to "т",
    "v" to "в",
    "y" to "й",
    "o" to "о",
    "q" to "қ",
    "x" to "х",
    "d" to "д",
    "y" to "й",
    "c" to "с",
    "w" to "в",
    "g'" to "ғ",
)

// Preconditions: input is lowercase
fun transliterate(input: String, map: Map<String, String> = transliterationMap): String {
    // If input contains uppercase letters, throw an exception
    if (input != input.lowercase()) {
        throw IllegalArgumentException("Input must be lowercase")
    }

    // If starts with "e" transliterate it to "э"
    if (input.startsWith("e")) {
        return transliterate("э" + input.substring(1))
    }

    val result = StringBuilder()
    var i = 0
    while (i < input.length) {
        if (i + 1 < input.length) {
            // Try to match a two-letter sequence
            val twoLetters = input.substring(i, i + 2)
            val transliterated = map[twoLetters]
            if (transliterated != null) {
                result.append(transliterated)
                i += 2
                continue
            }
        }

        // Try to match a single letter
        val transliterated = map[input.substring(i, i + 1)]
        if (transliterated != null) {
            result.append(transliterated)
        } else {
            result.append(input[i])  // Keep the original character if there's no mapping
        }
        i++
    }
    return result.toString()
}
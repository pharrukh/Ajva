package com.normuradov.ajva.utils

import org.junit.Assert.assertArrayEquals
import org.junit.Test

class ParseTextTest {
    @Test
    fun testParseText() {
        val text = "I have used the app,\n" +
                "when I just took photo of texts, it is recognizing its definition.\n" +
                "In some texts, it is not recognizing any way.\n" +
                "Baxt nima?\n" +
                "Ming bo'lakka bo'lib dunyoni,\n" +
                "Tug'ilgandan baxtni izlaymiz.\n" +
                "Qo'llaridan tutib ro'yoni\n" +
                "Atrofda baxt, ba'zan bilmaymiz.\n" +
                "Baxtlar bilan bezatilgan taxt,\n" +
                "Xudoyimga yetsa bas zoring\n" +
                "Kimdir seni sog'insa, shu Baxt,\n" +
                "Baxt aslida dunyoda boring.\n" +
                "Erkin Vohidov\n" +
                "When I took photo of this poem, it recognized\n" +
                "generated a text about Vijdon erkinligi"

        val expectedParsedText = listOf(
            "have",
            "used",
            "when",
            "just",
            "took",
            "photo",
            "texts",
            "recognizing",
            "definition",
            "some",
            "texts",
            "recognizing",
            "baxt",
            "nima",
            "ming",
            "bo'lakka",
            "bo'lib",
            "dunyoni",
            "tug'ilgandan",
            "baxtni",
            "izlaymiz",
            "qo'llaridan",
            "tutib",
            "ro'yoni",
            "atrofda",
            "baxt",
            "ba'zan",
            "bilmaymiz",
            "baxtlar",
            "bilan",
            "bezatilgan",
            "taxt",
            "xudoyimga",
            "yetsa",
            "zoring",
            "kimdir",
            "seni",
            "sog'insa",
            "baxt",
            "baxt",
            "aslida",
            "dunyoda",
            "boring",
            "erkin",
            "vohidov",
            "when",
            "took",
            "photo",
            "this",
            "poem",
            "recognized",
            "generated",
            "text",
            "about",
            "vijdon",
            "erkinligi"
        )

        val parsedText = parseRecognizedText(text)
        assertArrayEquals(expectedParsedText.toTypedArray(), parsedText.toTypedArray())
    }
}
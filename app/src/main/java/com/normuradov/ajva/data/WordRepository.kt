package com.normuradov.ajva.data

interface WordRepository {
    suspend fun get(id: String): Word
    suspend fun fullTextSearch(query: String): List<Word>
    suspend fun search(word: String): List<ExactWord>
}
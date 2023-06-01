package com.normuradov.ajva.data

interface WordRepository {
    suspend fun get(id: String): Word
    suspend fun search(query: String): List<Word>
}
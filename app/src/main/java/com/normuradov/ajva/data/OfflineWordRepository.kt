package com.normuradov.ajva.data

import androidx.compose.ui.text.toUpperCase

class OfflineWordRepository(private val dao: WordDao) : WordRepository {
    override suspend fun get(id: String): Word = dao.get(id)
    override suspend fun search(query: String): List<Word> = dao.search(query.uppercase())
    override suspend fun complexSearch(query: String): List<Word> = dao.complexSearch(query)
}
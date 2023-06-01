package com.normuradov.ajva.data

class OfflineWordRepository(private val dao: WordDao) : WordRepository {
    override suspend fun get(id: String): Word = dao.get(id)
    override suspend fun search(query: String): List<Word> = dao.search(query)
}
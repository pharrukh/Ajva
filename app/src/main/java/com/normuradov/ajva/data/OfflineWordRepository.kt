package com.normuradov.ajva.data

class OfflineWordRepository(private val dao: WordDao) : WordRepository {
    override suspend fun get(id: String): Word = dao.get(id)
    override suspend fun fullTextSearch(query: String): List<Word> =
        dao.fullTextSearch(query.uppercase())

    override suspend fun search(word: String): List<ExactWord> = dao.search(word)
}
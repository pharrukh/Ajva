package com.normuradov.ajva.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT rowid, word, meaning FROM words_fts WHERE word = :word LIMIT 1")
    suspend fun get(word: String): Word

    @Query("""
        SELECT rowid, word, meaning
        FROM words_fts
        WHERE word
            MATCH :query || '*'
        ORDER BY
            CASE
                WHEN word GLOB '[а-яА-Я]*' THEN 0
                ELSE 1
            END
        LIMIT 200""")
    suspend fun fullTextSearch(query: String): List<Word>

    @Query("SELECT * FROM words WHERE word LIKE :word || '%' LIMIT 1")
    suspend fun search(word: String): List<ExactWord>
}
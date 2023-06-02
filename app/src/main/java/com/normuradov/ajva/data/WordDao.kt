package com.normuradov.ajva.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT word, meaning FROM words_fts WHERE word = :word LIMIT 1")
    suspend fun get(word: String): Word

    @Query("""
        SELECT word, meaning
        FROM words_fts
        WHERE word
            MATCH :query || '*'
        ORDER BY
            CASE
                WHEN word GLOB '[а-яА-Я]*' THEN 0
                ELSE 1
            END,
            word
        LIMIT 50""")
    suspend fun search(query: String): List<Word>

    @Query("""
        SELECT word, meaning FROM words_fts WHERE word MATCH :query
        UNION
        SELECT word, meaning FROM words_fts WHERE meaning MATCH :query;
    """)
    suspend fun complexSearch(query: String): List<Word>
}
package com.normuradov.ajva.data

import androidx.room.Dao
import androidx.room.Query

@Dao
interface WordDao {
    @Query("SELECT * FROM dictionary WHERE id = :id")
    suspend fun get(id: String): Word

    @Query("SELECT * FROM dictionary WHERE word LIKE :query || '%'")
    suspend fun search(query: String): List<Word>
}
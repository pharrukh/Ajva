package com.normuradov.ajva.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey

@Fts4
@Entity(tableName = "words_fts")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Int,
    val word: String? = "",
    val meaning: String? = "",
)

@Entity (tableName = "words")
data class ExactWord(
    @PrimaryKey
    val id: Int? = null,
    val word: String? = "",
    val meaning: String? = "",
)
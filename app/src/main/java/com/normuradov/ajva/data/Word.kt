package com.normuradov.ajva.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Fts4
@Entity(tableName = "words_fts")
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Int,
    val word: String? = "",
    val meaning: String? = "",
)

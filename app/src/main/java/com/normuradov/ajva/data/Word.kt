package com.normuradov.ajva.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "dictionary")
data class Word(
    @PrimaryKey()
    val id: Int? = 0,
    val meaning: String? = "",
    val word: String? = "",
)

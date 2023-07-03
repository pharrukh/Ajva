package com.normuradov.ajva.data

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Fts4
@Entity(tableName = "words_fts")
@Parcelize
data class Word(
    @PrimaryKey
    @ColumnInfo(name = "rowid")
    val id: Int,
    val word: String? = "",
    val meaning: String? = "",
): Parcelable

@Entity (tableName = "words")
data class ExactWord(
    @PrimaryKey
    val id: Int? = null,
    val word: String? = "",
    val meaning: String? = "",
)
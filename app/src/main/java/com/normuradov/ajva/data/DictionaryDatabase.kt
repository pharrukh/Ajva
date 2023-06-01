package com.normuradov.ajva.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Word::class], version = 1, exportSchema = false)
abstract class DictionaryDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    companion object {
        @Volatile
        private var Instance: DictionaryDatabase? = null

        fun getDatabase(context: Context): DictionaryDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    DictionaryDatabase::class.java,
                    "dictionary_database"
                )
                    .createFromAsset("database/UZ_Lugati.db")
                    .fallbackToDestructiveMigration()
                    .build().also { Instance = it }
            }
        }
    }
}

package com.normuradov.ajva.data

import android.content.Context

interface AppContainer {
    val wordRepository: WordRepository
}

class AppDataContainer(
    private val context: Context,
) : AppContainer {
    override val wordRepository: WordRepository by lazy {
        OfflineWordRepository(DictionaryDatabase.getDatabase(context).wordDao())
    }
}
package com.normuradov.ajva

import android.app.Application
import com.normuradov.ajva.data.AppContainer
import com.normuradov.ajva.data.AppDataContainer
import com.normuradov.ajva.data.Word

class DictionaryApplication : Application() {
    var sharedFoundWords: List<Word> = emptyList()
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
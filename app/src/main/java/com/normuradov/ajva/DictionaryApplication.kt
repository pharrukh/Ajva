package com.normuradov.ajva

import android.app.Application
import com.normuradov.ajva.data.AppContainer
import com.normuradov.ajva.data.AppDataContainer

class DictionaryApplication : Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
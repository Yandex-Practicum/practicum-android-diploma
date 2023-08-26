package ru.practicum.android.diploma

import android.app.Application
import android.content.Context
import org.koin.core.context.startKoin
import org.koin.android.ext.koin.androidContext

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext

        startKoin{
            androidContext(this@App)
            modules()
        }
    }

    companion object {
        lateinit var context: Context
    }
}
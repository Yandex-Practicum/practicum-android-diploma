package ru.practicum.android.diploma

import android.app.Application
import android.content.Context

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    companion object {
        lateinit var context: Context
    }
}
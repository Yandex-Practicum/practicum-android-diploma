package ru.practicum.android.diploma.app

import android.app.Application
import ru.practicum.android.diploma.LoggerImpl
import ru.practicum.android.diploma.util.thisName

class App: Application() {

    private val logger = LoggerImpl()
    override fun onCreate() {
        super.onCreate()
        logger.log(thisName, "onCreate()")

    }
}
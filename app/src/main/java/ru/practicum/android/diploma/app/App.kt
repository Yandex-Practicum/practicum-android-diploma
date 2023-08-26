package ru.practicum.android.diploma.app

import android.app.Application
import ru.practicum.android.diploma.Logger
import ru.practicum.android.diploma.di.DaggerAppComponent
import ru.practicum.android.diploma.util.thisName
import javax.inject.Inject

class App: Application() {

    @Inject
    lateinit var logger: Logger
    val component by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }
    override fun onCreate() {
        component.inject(this)
        super.onCreate()
        logger.log(thisName, "onCreate() $logger")
    }
}
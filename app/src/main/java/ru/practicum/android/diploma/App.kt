package ru.practicum.android.diploma

import android.app.Application
import android.util.Log
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.appModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.domainModule
import ru.practicum.android.diploma.di.viewModelModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            Log.d("Koin", "Koin initialized")
            modules(listOf(appModule, dataModule, domainModule, viewModelModule))
        }
    }
}

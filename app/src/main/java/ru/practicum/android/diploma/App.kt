package ru.practicum.android.diploma

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.appModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.favouritesModule
import ru.practicum.android.diploma.di.filterModule
import ru.practicum.android.diploma.di.searchModule
import ru.practicum.android.diploma.di.vacancyModule

class App : Application() {

    override fun onCreate() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate()
        startKoin {
            androidContext(this@App)
            Log.d("Koin", "Koin initialized")
            modules(listOf(dataModule, appModule, favouritesModule, filterModule, searchModule, vacancyModule))
        }
    }
}

package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.appModule
import ru.practicum.android.diploma.di.dataModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule
import ru.practicum.android.diploma.di.viewModelModule

const val SHARED_PREFERENCES = "user_preferences"

class VacancyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@VacancyApplication)
            modules(listOf(appModule, dataModule, interactorModule, repositoryModule, viewModelModule))
        }
    }
}

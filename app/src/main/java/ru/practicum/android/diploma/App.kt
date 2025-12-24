package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.favorites.vacancies.di.favoritesVacanciesModules
import ru.practicum.android.diploma.search.di.searchModules
import ru.practicum.android.diploma.vacancy.details.di.vacancyDetailsModules

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val appModules = searchModules + favoritesVacanciesModules + vacancyDetailsModules
        startKoin {
            androidContext(this@App)
            modules(appModules)
        }
    }
}

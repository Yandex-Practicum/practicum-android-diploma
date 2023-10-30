package ru.practicum.android.diploma.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.di.favoriteModule
import ru.practicum.android.diploma.core.di.filterModule
import ru.practicum.android.diploma.core.di.detailsModule
import ru.practicum.android.diploma.core.di.searchModule
import ru.practicum.android.diploma.core.di.similarVacanciesModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                detailsModule,
                favoriteModule,
                searchModule,
                filterModule,
                similarVacanciesModule,
            )
        }
    }
}
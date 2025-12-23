package ru.practicum.android.diploma

import android.app.Application
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.favorites.vacancies.di.databaseModule
import ru.practicum.android.diploma.favorites.vacancies.di.favoritesRepositoryModule
import ru.practicum.android.diploma.favorites.vacancies.di.favoritesViewModelModule
import ru.practicum.android.diploma.search.di.interactorModule
import ru.practicum.android.diploma.search.di.networkModule
import ru.practicum.android.diploma.search.di.searchRepositoryModule
import ru.practicum.android.diploma.search.di.searchViewModelModule
import ru.practicum.android.diploma.search.di.utilsModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                utilsModule,
                networkModule,
                interactorModule,
                databaseModule,
                favoritesRepositoryModule,
                favoritesViewModelModule,
                searchRepositoryModule,
                searchViewModelModule,
            )
        }
    }
}

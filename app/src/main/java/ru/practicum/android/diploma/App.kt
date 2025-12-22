package ru.practicum.android.diploma

import android.app.Application
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.favorites.vacansies.di.databaseModule
import ru.practicum.android.diploma.search.di.networkModule
import ru.practicum.android.diploma.search.di.utilsModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                utilsModule,
                networkModule,
//                repositoryModule,
//                interactorModule,
                databaseModule
            )
        }
    }
}

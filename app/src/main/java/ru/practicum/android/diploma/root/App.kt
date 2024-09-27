package ru.practicum.android.diploma.root

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.data.networkclient.di.networkDataModule
import ru.practicum.android.diploma.favorites.di.favoritesModule
import ru.practicum.android.diploma.search.di.searchModule
import ru.practicum.android.diploma.team.di.teamModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                networkDataModule,
                searchModule,
                favoritesModule,
                teamModule,
            )
        }
    }
}

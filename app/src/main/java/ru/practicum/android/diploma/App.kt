package ru.practicum.android.diploma


import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import ru.practicum.android.diploma.di.DBModule
import ru.practicum.android.diploma.di.FavouriteModule
import ru.practicum.android.diploma.di.FavouriteViewModule
import ru.practicum.android.diploma.di.SearchModules

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                DBModule,
                FavouriteModule,
                FavouriteViewModule,
                SearchModules
            )
        }
    }
}


package ru.practicum.android.diploma.app

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.DataModule
import ru.practicum.android.diploma.di.InteractorModule
import ru.practicum.android.diploma.di.RepositoryModule
import ru.practicum.android.diploma.di.ViewModelModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(DataModule, InteractorModule, RepositoryModule, ViewModelModule)
        }
    }
}

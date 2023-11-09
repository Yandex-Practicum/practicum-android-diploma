package ru.practicum.android.diploma.core

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.core.di.dataModule
import ru.practicum.android.diploma.core.di.interactorModule
import ru.practicum.android.diploma.core.di.repositoryModule
import ru.practicum.android.diploma.core.di.viewModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dataModule,
                viewModelModule,
                interactorModule,
                repositoryModule,
            )
        }
    }
}
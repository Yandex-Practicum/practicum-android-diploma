package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.AppModule
import ru.practicum.android.diploma.di.DataBaseModule
import ru.practicum.android.diploma.di.InteractorModule
import ru.practicum.android.diploma.di.NetworkModule
import ru.practicum.android.diploma.di.RepositoryModule
import ru.practicum.android.diploma.di.ViewModelModule

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            printLogger()
            androidContext(this@App)
            modules(
                listOf(
                    AppModule,
                    DataBaseModule,
                    NetworkModule,
                    RepositoryModule,
                    InteractorModule,
                    ViewModelModule
                )
            )
        }
    }
}

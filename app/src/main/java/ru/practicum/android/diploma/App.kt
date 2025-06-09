package ru.practicum.android.diploma

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.practicum.android.diploma.di.dbModule
import ru.practicum.android.diploma.di.interactorModule
import ru.practicum.android.diploma.di.repositoryModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                dbModule,
                repositoryModule,
                interactorModule
            )
        }
        // Dependency Injection
        /**** подкл библиотеки в gradle добавить модули di****/
//        startKoin {
//            androidLogger(Level.DEBUG)
//            androidContext(this@App)
//            modules(listOf())
//        }
    }
}

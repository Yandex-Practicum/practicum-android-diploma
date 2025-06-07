package ru.practicum.android.diploma

import android.app.Application

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Dependency Injection
        /**** подкл библиотеки в gradle добавить модули di****/
//        startKoin {
//            androidLogger(Level.DEBUG)
//            androidContext(this@App)
//            modules(listOf())
//        }
    }
}

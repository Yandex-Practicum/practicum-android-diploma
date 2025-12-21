package ru.practicum.android.diploma

import android.app.Application
import ru.practicum.android.diploma.search.utils.NetworkConnectionChecker

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkConnectionChecker.init(this)
    }
}

package ru.practicum.android.diploma

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    companion object {
        const val DARK_THEME = "dark_theme_key"
        const val APPLICATION_PREFERENCES = "application_preferences"
        lateinit var sharedPreferences: SharedPreferences
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            // Модули Koin
        }

        // Retrofit
        // Glide
        // Другие библиотеки

        sharedPreferences = getSharedPreferences(APPLICATION_PREFERENCES, MODE_PRIVATE)

        // Установка темы (черновая), можете поменять, когда будете настраивать тему
        val darkTheme = sharedPreferences.getBoolean(DARK_THEME, false)
        AppCompatDelegate.setDefaultNightMode(
            if (darkTheme) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }
}

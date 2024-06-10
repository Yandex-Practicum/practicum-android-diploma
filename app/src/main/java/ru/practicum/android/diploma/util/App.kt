package ru.practicum.android.diploma.util

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    private var darkTheme = false
    // private var sharedPref: SharedPreferences = getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE)
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules()
        }
        initTheme()
    }

    fun initTheme() {
//        if (sharedPref.contains(_themeKey)) {
//            darkTheme = sharedPref.getBoolean(_themeKey, false)
//            switchTheme(darkTheme)
//        } else {
//            darkTheme = false
//        }
    }

    private fun switchTheme(darkThemeEnabled: Boolean) {
        darkTheme = darkThemeEnabled
        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
//        getSharedPreferences(PRACTICUM_EXAMPLE_PREFERENCES, MODE_PRIVATE).edit().putBoolean(_themeKey, darkThemeEnabled)
//            .apply()
    }
}

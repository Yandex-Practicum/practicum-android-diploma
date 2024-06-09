package ru.practicum.android.diploma.data

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.ThemeSettingsRepository

class ThemeSettingsRepositoryImpl(private val sharedPreferences: SharedPreferences) : ThemeSettingsRepository {

    private val THEME_KEY = "theme_key"
    override fun installTheme(): Boolean {
        val isThemeDark = readTheme()
        return switchTheme(isThemeDark)
    }

    override fun switchTheme(themeBooleanValue: Boolean): Boolean {
        AppCompatDelegate.setDefaultNightMode(
            if (themeBooleanValue) {
                writeTheme(true)
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                writeTheme(false)
                AppCompatDelegate.MODE_NIGHT_NO
            }
        )
        return readTheme()
    }

    private fun readTheme(): Boolean {
        val json = sharedPreferences.getString(THEME_KEY, false.toString()) ?: return false
        return Gson().fromJson(json, Boolean::class.java)
    }

    private fun writeTheme(themeBooleanValue: Boolean) {
        val json = Gson().toJson(themeBooleanValue)
        sharedPreferences.edit()
            .putString(THEME_KEY, json)
            .apply()
    }

    companion object {
        const val PRACTICUM_EXAMPLE_PREFERENCES = "practicum_example_preferences"
    }

}

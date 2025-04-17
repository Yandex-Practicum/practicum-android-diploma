package ru.practicum.android.diploma.data.storage

import android.content.Context

class AppPrefsService(context: Context) {

    private val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun putString(key: String, value: String) {
        prefs.edit().putString(key, value).apply()
    }

    fun getString(key: String): String? {
        return prefs.getString(key, null)
    }

    fun remove(key: String) {
        prefs.edit().remove(key).apply()
    }

    companion object {
        private const val PREF_NAME = "app_prefs"
    }
}

package ru.practicum.android.diploma.data.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.FilterSettingsRepository
import ru.practicum.android.diploma.domain.models.FilterSettings

class FilterSettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson
) : FilterSettingsRepository {

    override fun getFilterSettings(): FilterSettings {
        val json = sharedPreferences.getString(FILTER_SETTINGS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, FilterSettings::class.java)
        } else {
            FilterSettings()
        }
    }

    override fun saveFilterSettings(settings: FilterSettings) {
        val json = gson.toJson(settings)
        sharedPreferences.edit {
            putString(FILTER_SETTINGS_KEY, json)
        }
    }

    override fun clearFilterSettings() {
        sharedPreferences.edit {
            remove(FILTER_SETTINGS_KEY)
        }
    }

    companion object {
        private const val FILTER_SETTINGS_KEY = "filter_settings_key"
    }
}

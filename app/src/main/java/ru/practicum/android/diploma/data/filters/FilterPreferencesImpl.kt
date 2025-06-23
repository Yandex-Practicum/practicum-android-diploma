package ru.practicum.android.diploma.data.filters

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.FilterPreferences
import ru.practicum.android.diploma.ui.filter.model.SelectedFilters
import ru.practicum.android.diploma.util.FILTER_SETTINGS

class FilterPreferencesImpl(private val prefs: SharedPreferences) : FilterPreferences {
    override fun saveFilters(options: SelectedFilters) {
        val json = Gson().toJson(options)
        prefs.edit { putString(FILTER_SETTINGS, json) }
    }

    override fun loadFilters(): SelectedFilters? {
        val json = prefs.getString(FILTER_SETTINGS, null)
        return Gson().fromJson(json, SelectedFilters::class.java)
    }

    override fun clearFilters() {
        prefs.edit { remove(FILTER_SETTINGS) }
    }
}

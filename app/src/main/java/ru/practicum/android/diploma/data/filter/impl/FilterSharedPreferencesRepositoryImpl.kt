package ru.practicum.android.diploma.data.filter.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filter.FilterSharedPreferencesRepository
import ru.practicum.android.diploma.domain.models.Filter

class FilterSharedPreferencesRepositoryImpl(private val sharedPrefs: SharedPreferences) :
    FilterSharedPreferencesRepository {
    override fun getFilterSharedPrefs(): Filter? {
        val json = sharedPrefs.getString(USER_KEY, null) ?: return null
        return Gson().fromJson(json, Filter::class.java)
    }

    override fun setFilterSharedPrefs(filter: Filter) {
        val json = Gson().toJson(filter)
        sharedPrefs.edit()
            .putString(USER_KEY, json)
            .apply()
    }

    override fun clearFilterSharedPrefs() {
        sharedPrefs.edit().clear()
    }

    companion object {
        val USER_KEY = "filter"
    }
}

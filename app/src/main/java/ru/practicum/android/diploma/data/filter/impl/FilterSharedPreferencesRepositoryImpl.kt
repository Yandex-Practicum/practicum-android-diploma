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

    override fun setFilterSharedPrefs(newFilter: Filter) {
        val currentFilterJson = sharedPrefs.getString(USER_KEY, null)
        var currentFilter = if (currentFilterJson != null) {
            Gson().fromJson(currentFilterJson, Filter::class.java)
        } else {
            Filter()
        }
        if (newFilter.country != null) {
            currentFilter.country = newFilter.country
        }
        if (newFilter.region != null) {
            currentFilter.region = newFilter.region
        }
        if (newFilter.industry != null) {
            currentFilter.industry = newFilter.industry
        }
        if (newFilter.salary != null) {
            currentFilter.salary = newFilter.salary
        }
        if (newFilter.onlyWithSalary != null) {
            currentFilter.onlyWithSalary = newFilter.onlyWithSalary
        }

        val updatedJson = Gson().toJson(currentFilter)
        sharedPrefs.edit()
            .putString(USER_KEY, updatedJson)
            .apply()
    }

    override fun clearRegions(newFilter: Filter) {
        val currentFilterJson = sharedPrefs.getString(USER_KEY, null)
        var currentFilter = if (currentFilterJson != null) {
            Gson().fromJson(currentFilterJson, Filter::class.java)
        } else {
            Filter()
        }
        if (newFilter.country != null) {
            currentFilter.country = newFilter.country
        }

        currentFilter.region = newFilter.region

        if (newFilter.industry != null) {
            currentFilter.industry = newFilter.industry
        }
        if (newFilter.salary != null) {
            currentFilter.salary = newFilter.salary
        }
        if (newFilter.onlyWithSalary != null) {
            currentFilter.onlyWithSalary = newFilter.onlyWithSalary
        }

        val updatedJson = Gson().toJson(currentFilter)
        sharedPrefs.edit()
            .putString(USER_KEY, updatedJson)
            .apply()
    }

    override fun clearFilterSharedPrefs() {
        sharedPrefs.edit().clear().apply()
    }

    companion object {
        const val USER_KEY = "filter"
    }
}

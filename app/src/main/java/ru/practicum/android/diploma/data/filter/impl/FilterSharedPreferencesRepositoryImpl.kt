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
        val currentFilter = if (currentFilterJson != null) {
            Gson().fromJson(currentFilterJson, Filter::class.java)
        } else {
            Filter()
        }
        newFilter.country.let { currentFilter.country = it }
        newFilter.region.let { currentFilter.region = it }
        newFilter.industry.let { currentFilter.industry = it }
        newFilter.salary.let { currentFilter.salary = it }
        newFilter.onlyWithSalary.let { currentFilter.onlyWithSalary = it }

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

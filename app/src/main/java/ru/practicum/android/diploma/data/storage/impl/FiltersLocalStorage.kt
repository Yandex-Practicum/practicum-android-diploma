package ru.practicum.android.diploma.data.storage.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.data.storage.FiltersStorage
import ru.practicum.android.diploma.domain.filters.model.FiltersSettings

class FiltersLocalStorage(private val sharedPreferences: SharedPreferences) : FiltersStorage {

    private companion object {
        const val FILTERS_PLACE = "FILTERS_PLACE"
        const val FILTERS_INDUSTRY = "FILTERS_INDUSTRY"
        const val FILTERS_SALARY = "FILTERS_SALARY"
        const val FILTERS_SALARY_ONLY = "FILTERS_SALARY_ONLY"
    }

    override fun getPrefs(): FiltersSettings {
        return FiltersSettings (
            sharedPreferences.getString(FILTERS_PLACE, "") ?: "",
            sharedPreferences.getString(FILTERS_INDUSTRY, "") ?: "",
            sharedPreferences.getString(FILTERS_SALARY, "") ?: "",
            sharedPreferences.getBoolean(FILTERS_SALARY_ONLY, false) ?: false,
        )
    }

    override fun savePrefs(settings: FiltersSettings) {
        sharedPreferences.edit().putString(FILTERS_PLACE, settings.placeOfWork).apply()
        sharedPreferences.edit().putString(FILTERS_INDUSTRY, settings.industry).apply()
        sharedPreferences.edit().putString(FILTERS_SALARY, settings.expectedSalary).apply()
        sharedPreferences.edit().putBoolean(FILTERS_SALARY_ONLY, settings.salaryOnlyCheckbox).apply()
    }

    override fun clearPrefs() {
        sharedPreferences.edit().remove(FILTERS_PLACE).apply()
        sharedPreferences.edit().remove(FILTERS_INDUSTRY).apply()
        sharedPreferences.edit().remove(FILTERS_SALARY).apply()
        sharedPreferences.edit().remove(FILTERS_SALARY_ONLY).apply()
    }
}

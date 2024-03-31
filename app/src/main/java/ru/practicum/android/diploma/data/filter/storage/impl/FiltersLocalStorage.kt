package ru.practicum.android.diploma.data.filter.storage.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.data.filter.storage.FiltersStorage
import ru.practicum.android.diploma.domain.models.FiltersSettings

class FiltersLocalStorage(private val sharedPreferences: SharedPreferences) : FiltersStorage {

    companion object {
        const val FILTERS_COUNTRY = "FILTERS_COUNTRY"
        const val FILTERS_COUNTRY_ID = "FILTERS_COUNTRY_ID"
        const val FILTERS_REGION = "FILTERS_REGION"
        const val FILTERS_REGION_ID = "FILTERS_REGION_ID"
        const val FILTERS_INDUSTRY = "FILTERS_INDUSTRY"
        const val FILTERS_INDUSTRY_ID = "FILTERS_INDUSTRY_ID"
        const val FILTERS_SALARY = "FILTERS_SALARY"
        const val FILTERS_SALARY_ONLY = "FILTERS_SALARY_ONLY"
    }

    override fun getPrefs(): FiltersSettings {
        return FiltersSettings(
            sharedPreferences.getString(FILTERS_COUNTRY, "") ?: "",
            sharedPreferences.getString(FILTERS_COUNTRY_ID, "") ?: "",
            sharedPreferences.getString(FILTERS_REGION, "") ?: "",
            sharedPreferences.getString(FILTERS_REGION_ID, "") ?: "",
            sharedPreferences.getString(FILTERS_INDUSTRY, "") ?: "",
            sharedPreferences.getString(FILTERS_INDUSTRY_ID, "") ?: "",
            sharedPreferences.getString(FILTERS_SALARY, "") ?: "",
            sharedPreferences.getBoolean(FILTERS_SALARY_ONLY, false) ?: false,
        )
    }

    override fun savePrefs(settings: FiltersSettings) {
        sharedPreferences.edit().putString(FILTERS_COUNTRY, settings.country).apply()
        sharedPreferences.edit().putString(FILTERS_COUNTRY_ID, settings.countryId).apply()
        sharedPreferences.edit().putString(FILTERS_REGION, settings.region).apply()
        sharedPreferences.edit().putString(FILTERS_REGION_ID, settings.regionId).apply()
        sharedPreferences.edit().putString(FILTERS_INDUSTRY, settings.industry).apply()
        sharedPreferences.edit().putString(FILTERS_INDUSTRY_ID, settings.industryId).apply()
        sharedPreferences.edit().putString(FILTERS_SALARY, settings.expectedSalary).apply()
        sharedPreferences.edit().putBoolean(FILTERS_SALARY_ONLY, settings.salaryOnlyCheckbox).apply()
    }

    override fun clearPrefs() {
        sharedPreferences.edit().remove(FILTERS_COUNTRY).apply()
        sharedPreferences.edit().remove(FILTERS_COUNTRY_ID).apply()
        sharedPreferences.edit().remove(FILTERS_REGION).apply()
        sharedPreferences.edit().remove(FILTERS_REGION_ID).apply()
        sharedPreferences.edit().remove(FILTERS_INDUSTRY).apply()
        sharedPreferences.edit().remove(FILTERS_INDUSTRY_ID).apply()
        sharedPreferences.edit().remove(FILTERS_SALARY).apply()
        sharedPreferences.edit().remove(FILTERS_SALARY_ONLY).apply()
    }
}

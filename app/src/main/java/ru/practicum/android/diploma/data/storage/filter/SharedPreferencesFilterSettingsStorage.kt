package ru.practicum.android.diploma.data.storage.filter

import android.content.SharedPreferences
import androidx.core.content.edit
import ru.practicum.android.diploma.domain.models.FilterSettings

class SharedPreferencesFilterSettingsStorage(
    private val sharedPreferences: SharedPreferences,
) : FilterSettingsStorage {

    override fun getFilterSettings(): FilterSettings {
        return FilterSettings(
            salary = sharedPreferences.getString(KEY_SALARY, null).orEmpty(),
            onlyWithSalary = sharedPreferences.getBoolean(KEY_ONLY_WITH_SALARY, false),
            countryId = sharedPreferences.getNullableInt(KEY_COUNTRY_ID),
            countryName = sharedPreferences.getString(KEY_COUNTRY_NAME, null),
            regionId = sharedPreferences.getNullableInt(KEY_REGION_ID),
            regionName = sharedPreferences.getString(KEY_REGION_NAME, null),
            industryId = sharedPreferences.getNullableInt(KEY_INDUSTRY_ID),
            industryName = sharedPreferences.getString(KEY_INDUSTRY_NAME, null),
        )
    }

    override fun saveFilterSettings(settings: FilterSettings) {
        sharedPreferences.edit {
            putString(KEY_SALARY, settings.salary)
            putBoolean(KEY_ONLY_WITH_SALARY, settings.onlyWithSalary)
            putNullableInt(KEY_COUNTRY_ID, settings.countryId)
            putString(KEY_COUNTRY_NAME, settings.countryName)
            putNullableInt(KEY_REGION_ID, settings.regionId)
            putString(KEY_REGION_NAME, settings.regionName)
            putNullableInt(KEY_INDUSTRY_ID, settings.industryId)
            putString(KEY_INDUSTRY_NAME, settings.industryName)
        }
    }

    override fun clearFilterSettings() {
        sharedPreferences.edit { clear() }
    }

    private fun SharedPreferences.getNullableInt(key: String): Int? {
        return if (contains(key)) getInt(key, DEFAULT_ID) else null
    }

    private fun SharedPreferences.Editor.putNullableInt(key: String, value: Int?) {
        if (value == null) {
            remove(key)
        } else {
            putInt(key, value)
        }
    }

    companion object {
        private const val DEFAULT_ID = 0
        private const val KEY_SALARY = "salary"
        private const val KEY_ONLY_WITH_SALARY = "only_with_salary"
        private const val KEY_COUNTRY_ID = "country_id"
        private const val KEY_COUNTRY_NAME = "country_name"
        private const val KEY_REGION_ID = "region_id"
        private const val KEY_REGION_NAME = "region_name"
        private const val KEY_INDUSTRY_ID = "industry_id"
        private const val KEY_INDUSTRY_NAME = "industry_name"
    }
}

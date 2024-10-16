package ru.practicum.android.diploma.filters.data.SharedPreferences

import android.content.SharedPreferences

class SaveFilterSettingsStorage(
    private val sharedPreferences: SharedPreferences
)  {

    fun saveFiltersSettings() {
        TODO("Not yet implemented")
    }

    fun getFiltersSettings() {
        TODO("Not yet implemented")
    }

    companion object {
        private const val COUNTRY_ID = "country_id"
        private const val CITY_ID = "city_id"
        private const val SALARY = "salary"
        private const val IS_SHOW_WITH_SALARY = "is_show_with_salary"
    }
}

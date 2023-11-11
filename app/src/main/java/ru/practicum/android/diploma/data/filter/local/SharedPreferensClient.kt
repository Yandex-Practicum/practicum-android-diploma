package ru.practicum.android.diploma.data.filter.local

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.filter.Country

class SharedPreferensClient(val gson: Gson, private val sharedPreferences: SharedPreferences) :
    LocalStorage {

    override fun setSalary(salary: String) {
        sharedPreferences.edit()
            .putString(SALARY_KEY, salary)
            .apply()
    }

    override fun getSalary(): String {
        return sharedPreferences.getString(SALARY_KEY, "") ?: ""
    }

    override fun setSelectedCountry(country: Country?) {
        val countryJson = gson.toJson(country)
        sharedPreferences.edit()
            .putString(SELECTED_COUNTRY_KEY, countryJson)
            .apply()
    }

    override fun getSelectedCountry(): Country? {
        val countryJson = sharedPreferences.getString(SELECTED_COUNTRY_KEY, null)
        return gson.fromJson(countryJson, Country::class.java)
    }

    companion object {
        const val SALARY_KEY = "salary"
        const val SELECTED_COUNTRY_KEY = "selectedCountry"
    }
}

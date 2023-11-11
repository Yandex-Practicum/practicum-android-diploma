package ru.practicum.android.diploma.data.filter.local

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.filter.Country

class SharedPreferensClient(val gson: Gson, private val sharedPreferences: SharedPreferences) :
    LocalStorage {

    private val selectedCountryLiveData = MutableLiveData<Country?>()

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

        // Обновление LiveData при изменении выбранной страны
        selectedCountryLiveData.value = country
    }

    override fun getSelectedCountry(): Country? {
        val countryJson = sharedPreferences.getString(SELECTED_COUNTRY_KEY, null)
        return gson.fromJson(countryJson, Country::class.java)
    }

    override fun getSelectedCountryLiveData(): LiveData<Country?> {
        return selectedCountryLiveData
    }

    companion object {
        const val SALARY_KEY = "salary"
        const val SELECTED_COUNTRY_KEY = "selectedCountry"
    }
}

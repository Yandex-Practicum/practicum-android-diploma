package ru.practicum.android.diploma.data.filter.storage.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filter.storage.FiltersStorage
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared
import ru.practicum.android.diploma.domain.models.FiltersSettings

class FiltersLocalStorage(private val sharedPreferences: SharedPreferences) : FiltersStorage {

    private val gson = Gson()

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

    fun saveCountryState(country: CountryShared?) {
        val json = gson.toJson(country)
        sharedPreferences.edit().putString(KEY_COUNTRY, json).apply()
    }

    fun loadCountryState(): CountryShared? {
        val json = sharedPreferences.getString(KEY_COUNTRY, null)
        return gson.fromJson(json, CountryShared::class.java)
    }

    fun saveRegionState(region: RegionShared?) {
        val json = gson.toJson(region)
        sharedPreferences.edit().putString(KEY_REGION, json).apply()
    }

    fun loadRegionState(): RegionShared? {
        val json = sharedPreferences.getString(KEY_REGION, null)
        return gson.fromJson(json, RegionShared::class.java)
    }

    fun saveIndustriesState(industries: IndustriesShared?) {
        val json = gson.toJson(industries)
        sharedPreferences.edit().putString(KEY_INDUSTRIES, json).apply()
    }

    fun loadIndustriesState(): IndustriesShared? {
        val json = sharedPreferences.getString(KEY_INDUSTRIES, null)
        return gson.fromJson(json, IndustriesShared::class.java)
    }

    fun saveSalaryTextState(salary: SalaryTextShared?) {
        val json = gson.toJson(salary)
        sharedPreferences.edit().putString(KEY_SALARY_TEXT, json).apply()
    }

    fun loadSalaryTextState(): SalaryTextShared? {
        val json = sharedPreferences.getString(KEY_SALARY_TEXT, null)
        return gson.fromJson(json, SalaryTextShared::class.java)
    }

    fun saveSalaryBooleanState(salary: SalaryBooleanShared?) {
        val json = gson.toJson(salary)
        sharedPreferences.edit().putString(KEY_SALARY_BOOLEAN, json).apply()
    }

    fun loadSalaryBooleanState(): SalaryBooleanShared? {
        val json = sharedPreferences.getString(KEY_SALARY_BOOLEAN, null)
        return gson.fromJson(json, SalaryBooleanShared::class.java)
    }

    companion object {
        const val FILTERS_COUNTRY = "FILTERS_COUNTRY"
        const val FILTERS_COUNTRY_ID = "FILTERS_COUNTRY_ID"
        const val FILTERS_REGION = "FILTERS_REGION"
        const val FILTERS_REGION_ID = "FILTERS_REGION_ID"
        const val FILTERS_INDUSTRY = "FILTERS_INDUSTRY"
        const val FILTERS_INDUSTRY_ID = "FILTERS_INDUSTRY_ID"
        const val FILTERS_SALARY = "FILTERS_SALARY"
        const val FILTERS_SALARY_ONLY = "FILTERS_SALARY_ONLY"

        const val KEY_COUNTRY = "country"
        const val KEY_REGION = "region"
        const val KEY_INDUSTRIES = "industries"
        const val KEY_SALARY_TEXT = "salary_text"
        const val KEY_SALARY_BOOLEAN = "salary_boolean"
    }
}

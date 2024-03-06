package ru.practicum.android.diploma.core.sharedpreferences

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filter.domain.models.Filter

class FilterStorageImpl(private val sharedPreferences: SharedPreferences, private val gson: Gson) : FilterStorage {
    override fun saveCountry(country: String) {
        sharedPreferences.edit()
            .putString(COUNTRY_KEY, country)
            .apply()
    }

    override fun saveRegion(region: String) {
        sharedPreferences.edit()
            .putString(REGION_KEY, region)
            .apply()
    }

    override fun saveIndustry(industry: String) {
        sharedPreferences.edit()
            .putString(INDUSTRY_KEY, industry)
            .apply()
    }

    override fun saveSalary(salary: Int) {
        sharedPreferences.edit()
            .putInt(SALARY_KEY, salary)
            .apply()
    }

    override fun saveShowWithSalary(isChecked: Boolean) {
        sharedPreferences.edit()
            .putBoolean(IsCHECKED_KEY, isChecked)
            .apply()
    }

    override fun getCountry(): String {
        return sharedPreferences.getString(COUNTRY_KEY, "") ?: ""
    }

    override fun getRegion(): String {
        return sharedPreferences.getString(REGION_KEY, "") ?: ""
    }

    override fun getIndustry(): String {
        return sharedPreferences.getString(INDUSTRY_KEY, "") ?: ""
    }

    override fun getSalary(): Int {
        return sharedPreferences.getInt(SALARY_KEY, 0)
    }

    override fun getShowWithSalary(): Boolean {
        return sharedPreferences.getBoolean(IsCHECKED_KEY, false)
    }

    override fun deleteCountry() {
        sharedPreferences.edit()
            .remove(COUNTRY_KEY)
            .apply()
    }

    override fun deleteRegion() {
        sharedPreferences.edit()
            .remove(REGION_KEY)
            .apply()
    }

    override fun deleteIndustry() {
        sharedPreferences.edit()
            .remove(INDUSTRY_KEY)
            .apply()
    }

    override fun deleteSalary() {
        sharedPreferences.edit()
            .remove(SALARY_KEY)
            .apply()
    }

    override fun deleteShowWithSalary() {
        sharedPreferences.edit()
            .remove(IsCHECKED_KEY)
            .apply()
    }

    override fun getFilter(): Filter {
        val country = getCountry()
        val region = getRegion()
        val industry = getIndustry()
        val salary = getSalary()
        val showWithSalary = getShowWithSalary()
        var filter = Filter(
            country = country,
            region = region,
            industry = industry,
            salary = salary,
            showWithSalary = showWithSalary
        )
        val filtersJson = sharedPreferences.getString(FILTER_KEY, null)
        if (filtersJson != null) {
            val savedFilter = gson.fromJson(filtersJson, Filter::class.java)
            filter = savedFilter
        }
        return filter
    }

    override fun clearFilter() {
        deleteCountry()
        deleteRegion()
        deleteIndustry()
        deleteSalary()
        deleteShowWithSalary()
    }

    companion object {
        private const val FILTER_KEY = "FILTER_KEY"
        private const val COUNTRY_KEY = "COUNTRY_KEY"
        private const val REGION_KEY = "REGION_KEY"
        private const val INDUSTRY_KEY = "INDUSTRY_KEY"
        private const val SALARY_KEY = "SALARY_KEY"
        private const val IsCHECKED_KEY = "IsCHECKED_KEY"
    }
}

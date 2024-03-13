package ru.practicum.android.diploma.domain.filter.impl

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.practicum.android.diploma.domain.filter.FilterInfoRepository
import ru.practicum.android.diploma.domain.filter.datashared.CountryShared
import ru.practicum.android.diploma.domain.filter.datashared.IndustriesShared
import ru.practicum.android.diploma.domain.filter.datashared.RegionShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryBooleanShared
import ru.practicum.android.diploma.domain.filter.datashared.SalaryTextShared

class FilterInfoRepositoryImpl(private val context: Context) : FilterInfoRepository {



    private val gson = Gson()

     val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    private val countryFlow: MutableStateFlow<CountryShared?> = MutableStateFlow(loadCountryState())
    private val regionFlow: MutableStateFlow<RegionShared?> = MutableStateFlow(loadRegionState())
    private val industriesFlow: MutableStateFlow<IndustriesShared?> = MutableStateFlow(loadIndustriesState())
    private val salaryTextFlow: MutableStateFlow<SalaryTextShared?> = MutableStateFlow(loadSalaryTextState())
    private val salaryBooleanFlow: MutableStateFlow<SalaryBooleanShared?> = MutableStateFlow(loadSalaryBooleanState())

    override fun setCountryFlow(country: CountryShared?) {
        countryFlow.value = country
        saveCountryState(country)
    }

    override fun getCountryFlow(): StateFlow<CountryShared?> = countryFlow

    override fun setRegionFlow(region: RegionShared?) {
        regionFlow.value = region
        saveRegionState(region)
    }

    override fun getRegionFlow(): StateFlow<RegionShared?> = regionFlow

    override fun setIndustriesFlow(industries: IndustriesShared?) {
        industriesFlow.value = industries
        saveIndustriesState(industries)
    }

    override fun getIndustriesFlow(): StateFlow<IndustriesShared?> = industriesFlow
    override fun setSalaryTextFlow(salaryText: SalaryTextShared?) {
        salaryTextFlow.value = salaryText
        saveSalaryTextState(salaryText)
    }

    override fun getSalaryTextFlow(): StateFlow<SalaryTextShared?> = salaryTextFlow
    override fun setSalaryBooleanFlow(salaryBoolean: SalaryBooleanShared?) {
        salaryBooleanFlow.value = salaryBoolean
        saveSalaryBooleanState(salaryBoolean)
    }

    override fun getSalaryBooleanFlow(): StateFlow<SalaryBooleanShared?> = salaryBooleanFlow

    private fun saveCountryState(country: CountryShared?) {
        val json = gson.toJson(country)
        sharedPreferences.edit().putString(KEY_COUNTRY, json).apply()
    }

    private fun loadCountryState(): CountryShared? {
        val json = sharedPreferences.getString(KEY_COUNTRY, null)
        return gson.fromJson(json, CountryShared::class.java)
    }

    private fun saveRegionState(region: RegionShared?) {
        val json = gson.toJson(region)
        sharedPreferences.edit().putString(KEY_REGION, json).apply()
    }

    private fun loadRegionState(): RegionShared? {
        val json = sharedPreferences.getString(KEY_REGION, null)
        return gson.fromJson(json, RegionShared::class.java)
    }

    private fun saveIndustriesState(industries: IndustriesShared?) {
        val json = gson.toJson(industries)
        sharedPreferences.edit().putString(KEY_INDUSTRIES, json).apply()
    }

    private fun loadIndustriesState(): IndustriesShared? {
        val json = sharedPreferences.getString(KEY_INDUSTRIES, null)
        return gson.fromJson(json, IndustriesShared::class.java)
    }

    private fun saveSalaryTextState(salary: SalaryTextShared?) {
        val json = gson.toJson(salary)
        sharedPreferences.edit().putString(KEY_SALARY_TEXT, json).apply()
    }

    private fun loadSalaryTextState(): SalaryTextShared? {
        val json = sharedPreferences.getString(KEY_SALARY_TEXT, null)
        return gson.fromJson(json, SalaryTextShared::class.java)
    }

    private fun saveSalaryBooleanState(salary: SalaryBooleanShared?) {
        val json = gson.toJson(salary)
        sharedPreferences.edit().putString(KEY_SALARY_BOOLEAN, json).apply()
    }

    private fun loadSalaryBooleanState(): SalaryBooleanShared? {
        val json = sharedPreferences.getString(KEY_SALARY_BOOLEAN, null)
        return gson.fromJson(json, SalaryBooleanShared::class.java)
    }

    companion object {
         const val PREFS_NAME = "FilterInfoPrefs"
         const val KEY_COUNTRY = "country"
         const val KEY_REGION = "region"
         const val KEY_INDUSTRIES = "industries"
         const val KEY_SALARY_TEXT = "salary_text"
         const val KEY_SALARY_BOOLEAN = "salary_boolean"
    }
}

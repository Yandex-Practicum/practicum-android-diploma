package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun setArea(areaId: String) {
        searchRequest["area"] = areaId
        saveFilterValueInSharedPrefs(SAVED_AREA, areaId)
    }

    override fun setIndustry(industry: Industry) {
        searchRequest["industry"] = industry.id
        saveFilterValueInSharedPrefs(SAVED_INDUSTRY, gson.toJson(industry))
    }

    override fun setSalary(salary: String) {
        searchRequest["salary"] = salary
        saveFilterValueInSharedPrefs(SAVED_SALARY, salary)
    }

    override fun setCurrency(currency: String) {
        searchRequest["currency"] = currency
        saveFilterValueInSharedPrefs(SAVED_CURRENCY, currency)
    }

    override fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        searchRequest["only_with_salary"] = isShowWithSalary.toString()
        saveFilterValueInSharedPrefs(SAVED_SHOW_WITH_SALARY, isShowWithSalary.toString())
    }

    override fun getRequest(): HashMap<String, String> {
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        return SavedFilters(
            savedArea = sharedPreferences.getString(SAVED_AREA, ""),
            savedIndustry = getIndustryName(
                sharedPreferences.getString(SAVED_INDUSTRY, "")
            ),
            savedCurrency = sharedPreferences.getString(SAVED_CURRENCY, ""),
            savedSalary = sharedPreferences.getString(SAVED_SALARY, ""),
            savedIsShowWithSalary = sharedPreferences.getBoolean(SAVED_SHOW_WITH_SALARY, false)
        )
    }

    private fun saveFilterValueInSharedPrefs(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun getIndustryName(json: String?): String? {
        return if (json == null) null
        else {
            val industry = gson.fromJson(json, Industry::class.java)
            industry.name
        }
    }

    companion object {
        const val SAVED_AREA = "savedArea"
        const val SAVED_INDUSTRY = "savedIndustry"
        const val SAVED_CURRENCY = "savedCurrency"
        const val SAVED_SALARY = "savedSalary"
        const val SAVED_SHOW_WITH_SALARY = "savedShowWithSalary"

    }
}

package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderRepositoryImpl(private val sharedPreferences: SharedPreferences) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun setArea(areaId: String) {
        searchRequest["area"] = areaId
        saveFilterValueInSharedPrefs(SAVED_AREA, areaId)
    }

    override fun setIndustry(industryId: String) {
        searchRequest["industry"] = industryId
        saveFilterValueInSharedPrefs(SAVED_INDUSTRY, industryId)
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
            savedIndustry = sharedPreferences.getString(SAVED_INDUSTRY, ""),
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

    companion object {
        const val SAVED_AREA = "savedArea"
        const val SAVED_INDUSTRY = "savedIndustry"
        const val SAVED_CURRENCY = "savedCurrency"
        const val SAVED_SALARY = "savedSalary"
        const val SAVED_SHOW_WITH_SALARY = "savedShowWithSalary"

    }
}

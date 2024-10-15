package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderRepositoryImpl(private val sharedPreferences: SharedPreferences) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun saveArea(areaId: String) {
        searchRequest["area"] = areaId
        sharedPreferences.edit()
            .putString(SAVED_AREA, areaId)
            .apply()
    }

    override fun saveIndustry(industryId: String) {
        searchRequest["industry"] = industryId
        sharedPreferences.edit()
            .putString(SAVED_INDUSTRY, industryId)
            .apply()
    }

    override fun saveSalary(salary: String) {
        searchRequest["salary"] = salary
        sharedPreferences.edit()
            .putString(SAVED_SALARY, salary)
            .apply()
    }

    override fun saveCurrency(currency: String) {
        searchRequest["currency"] = currency
        sharedPreferences.edit()
            .putString(SAVED_CURRENCY, currency)
            .apply()
    }

    override fun saveIsShowWithSalary(isShowWithSalary: Boolean) {
        searchRequest["only_with_salary"] = isShowWithSalary.toString()
        sharedPreferences.edit()
            .putString(SAVED_SHOW_WITH_SALARY, isShowWithSalary.toString())
            .apply()
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

    companion object {
        const val SAVED_AREA = "savedArea"
        const val SAVED_INDUSTRY = "savedIndustry"
        const val SAVED_CURRENCY = "savedCurrency"
        const val SAVED_SALARY = "savedSalary"
        const val SAVED_SHOW_WITH_SALARY = "savedShowWithSalary"

    }
}

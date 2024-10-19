package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import ru.practicum.android.diploma.filters.areas.domain.api.AreaCashRepository
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
    private val areaCashRepository: AreaCashRepository
) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        searchRequest["text"] = text
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

    override fun cleanIndustry() {
        saveFilterValueInSharedPrefs(SAVED_INDUSTRY, "")
    }

    override fun getRequest(): HashMap<String, String> {
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        areaCashRepository.resetCashArea()
        return SavedFilters(
            savedArea = getArea(),
            savedIndustry = getIndustry(
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

    private fun getIndustry(json: String?): Industry? {
        if (json.isNullOrEmpty()) return null
        return try {
            gson.fromJson(json, Industry::class.java)
        } catch (e: JsonParseException) {
            null
        }
    }

    private fun getArea(): Area? {
        val json = sharedPreferences.getString(SAVED_AREA, "")
        if (json.isNullOrEmpty()) return null
        return try {
            gson.fromJson(json, Area::class.java)

        } catch (e: JsonParseException) {
            null
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

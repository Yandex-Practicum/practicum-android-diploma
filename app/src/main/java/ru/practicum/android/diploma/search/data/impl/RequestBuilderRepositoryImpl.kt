package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.JsonParseException
import ru.practicum.android.diploma.filters.areas.domain.models.Area
import ru.practicum.android.diploma.filters.industries.domain.models.Industry
import ru.practicum.android.diploma.search.data.model.SavedFilters
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository

class RequestBuilderRepositoryImpl(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()
    private var areaForSave: Area? = getArea()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun setCashArea(area: Area) {
        areaForSave = area
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

    override fun cleanArea() {
        areaForSave = null
        saveFilterValueInSharedPrefs(SAVED_AREA, "")
    }

    override fun getRequest(): HashMap<String, String> {
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        areaForSave = getArea()
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

    override fun cleanCashRegion() {
        areaForSave = areaForSave?.copy(id = "", name = "")
    }

    override fun cleanCashArea() {
        areaForSave = null
    }

    override fun saveArea() {
        val area: Area
        if (areaForSave != null) {
            area = areaForSave!!
            if (area.id.isNotBlank()) {
                searchRequest["area"] = area.id
            } else if (!area.parentId.isNullOrBlank()) {
                searchRequest["area"] = area.parentId
            }
            saveFilterValueInSharedPrefs(SAVED_AREA, gson.toJson(areaForSave))
        } else {
            saveFilterValueInSharedPrefs(SAVED_AREA, "")
        }
    }

    override fun getCashArea(): Area? {
        return areaForSave
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

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
    private var areaForSave: Area? = null

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun setArea(area: Area) {
        searchRequest["area"] = area.id

        if (areaForSave == null) areaForSave = area

        if (area.parentId == null && areaForSave?.parentName == null) {
            areaForSave = areaForSave?.copy(parentName = area.name)
        } else if (area.parentId == null && areaForSave?.parentName != null) {
            areaForSave = areaForSave?.copy(parentName = area.name, name = area.name)
        } else if (areaForSave?.parentName != null) {
            areaForSave = areaForSave?.copy(name = area.name)
        } else {
            areaForSave = areaForSave?.copy(parentName = "Тут запрос делать я хз?")
        }

        saveFilterValueInSharedPrefs(SAVED_AREA, gson.toJson(areaForSave))
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
        saveFilterValueInSharedPrefs(SAVED_AREA, "")
    }

    override fun getRequest(): HashMap<String, String> {
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        return SavedFilters(
            savedArea = getAreaName(
                sharedPreferences.getString(SAVED_AREA, "")
            ),
            savedIndustry = getIndustryName(
                sharedPreferences.getString(SAVED_INDUSTRY, "")
            ),
            savedCurrency = sharedPreferences.getString(SAVED_CURRENCY, ""),
            savedSalary = sharedPreferences.getString(SAVED_SALARY, ""),
            savedIsShowWithSalary = sharedPreferences
                .getString(SAVED_SHOW_WITH_SALARY, false.toString()).toBoolean()
        )
    }

    private fun saveFilterValueInSharedPrefs(key: String, value: String) {
        sharedPreferences.edit()
            .putString(key, value)
            .apply()
    }

    private fun getIndustryName(json: String?): String? {
        if (json.isNullOrEmpty()) return null
        return try {
            val industry = gson.fromJson(json, Industry::class.java)
            industry.name
        } catch (e: JsonParseException) {
            null
        }
    }

    private fun getAreaName(json: String?): String? {
        if (json.isNullOrEmpty()) return null
        return try {
            val area = gson.fromJson(json, Area::class.java)
            if (area.parentName == area.name) {
                "${area.parentName}"
            } else {
                "${area.parentName}, ${area.name}"
            }

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

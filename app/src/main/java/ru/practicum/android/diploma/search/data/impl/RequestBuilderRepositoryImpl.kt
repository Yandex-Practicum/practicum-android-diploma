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
    private var bufferedSavedFilters = getSavedFilters()
    private val searchRequest: HashMap<String, String> = HashMap()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun setIndustry(industry: Industry) {
        bufferedSavedFilters = bufferedSavedFilters.copy(savedIndustry = industry)
    }

    override fun setSalary(salary: String) {
        bufferedSavedFilters = bufferedSavedFilters.copy(savedSalary = salary)
    }

    override fun setCurrency(currency: String) {
        saveFilterValueInSharedPrefs(SAVED_CURRENCY, currency)
    }

    override fun setIsShowWithSalary(isShowWithSalary: Boolean) {
        bufferedSavedFilters = bufferedSavedFilters.copy(savedIsShowWithSalary = isShowWithSalary)
    }

    override fun cleanIndustry() {
        saveFilterValueInSharedPrefs(SAVED_INDUSTRY, "")
    }

    override fun getRequest(): HashMap<String, String> {
        searchRequest.clear()
        val savedFilters = getSavedFilters()
        if (savedFilters.savedArea != null) {
            if (savedFilters.savedArea.id.isNotBlank()) {
                searchRequest["area"] = savedFilters.savedArea.id
            } else if (savedFilters.savedArea.parentId?.isNotBlank() == true) {
                searchRequest["area"] = savedFilters.savedArea.parentId
            }
        }
        if (savedFilters.savedIndustry?.id?.isNotBlank() == true) {
            searchRequest["industry"] = savedFilters.savedIndustry.id
        }
        if (savedFilters.savedSalary?.isNotBlank() == true) {
            searchRequest["salary"] = savedFilters.savedSalary
        }
        if (savedFilters.savedIsShowWithSalary?.toString()?.isNotBlank() == true) {
            searchRequest["only_with_salary"] = savedFilters.savedIsShowWithSalary.toString()
        }
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        return SavedFilters(
            savedArea = getArea(),
            savedIndustry = getIndustry(
                sharedPreferences.getString(SAVED_INDUSTRY, "")
            ),
            savedCurrency = sharedPreferences.getString(SAVED_CURRENCY, ""),
            savedSalary = sharedPreferences.getString(SAVED_SALARY, ""),
            savedIsShowWithSalary = sharedPreferences
                .getString(SAVED_SHOW_WITH_SALARY, false.toString()).toBoolean()
        )
    }

    override fun updateBufferedSavedFilters(newBufferedSavedFilters: SavedFilters) {
        bufferedSavedFilters = newBufferedSavedFilters
    }

    override fun getBufferedSavedFilters(): SavedFilters {
        return bufferedSavedFilters
    }

    override fun saveFiltersToShared() {
        saveFilterValueInSharedPrefs(SAVED_SALARY, bufferedSavedFilters.savedSalary ?: "")
        saveFilterValueInSharedPrefs(SAVED_INDUSTRY, gson.toJson(bufferedSavedFilters.savedIndustry))
        saveFilterValueInSharedPrefs(SAVED_AREA, gson.toJson(bufferedSavedFilters.savedArea))
        saveFilterValueInSharedPrefs(SAVED_SHOW_WITH_SALARY, bufferedSavedFilters.savedIsShowWithSalary.toString())
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

    override fun clearAllFilters() {
        bufferedSavedFilters = bufferedSavedFilters.copy(
            savedArea = null,
            savedIndustry = null,
            savedCurrency = "",
            savedSalary = "",
            savedIsShowWithSalary = false
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

package ru.practicum.android.diploma.search.data.impl

import android.content.SharedPreferences
import com.google.gson.GsonBuilder
import ru.practicum.android.diploma.search.domain.api.RequestBuilderRepository
import ru.practicum.android.diploma.search.domain.api.SavedFilters

class RequestBuilderRepositoryImpl(private val sharedPreferences: SharedPreferences) : RequestBuilderRepository {
    private val searchRequest: HashMap<String, String> = HashMap()
    private val gson = GsonBuilder().setPrettyPrinting().create()

    override fun setText(text: String) {
        searchRequest["text"] = text
    }

    override fun saveArea(areaId: String) {
        searchRequest["area"] = areaId
        sharedPreferences.edit()
            .putString("savedArea", gson.toJson(areaId))
            .apply()
    }

    override fun saveSalary(salary: String) {
        searchRequest["salary"] = salary
        sharedPreferences.edit()
            .putString("savedSalary", gson.toJson(salary))
            .apply()
    }

    override fun saveCurrency(currency: String) {
        searchRequest["currency"] = currency
        sharedPreferences.edit()
            .putString("savedCurrency", gson.toJson(currency))
            .apply()
    }

    override fun saveIsShowWithSalary(isShowWithSalary: Boolean) {
        searchRequest["only_with_salary"] = isShowWithSalary.toString()
        sharedPreferences.edit()
            .putString("savedShowWithSalary", gson.toJson(isShowWithSalary.toString()))
            .apply()
    }

    override fun getRequest(): HashMap<String, String> {
        return searchRequest
    }

    override fun getSavedFilters(): SavedFilters {
        return SavedFilters(
            savedArea = sharedPreferences.getString("savedArea", ""),
            savedCurrency = sharedPreferences.getString("savedCurrency", ""),
            savedSalary = sharedPreferences.getString("savedSalary", ""),
            savedIsShowWithSalary = sharedPreferences.getBoolean("savedShowWithSalary", false)
        )
    }
}

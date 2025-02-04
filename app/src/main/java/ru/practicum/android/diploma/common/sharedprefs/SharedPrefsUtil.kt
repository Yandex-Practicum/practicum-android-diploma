package ru.practicum.android.diploma.common.sharedprefs

import android.content.Context
import com.google.gson.Gson
import ru.practicum.android.diploma.common.sharedprefs.models.Filter

class SharedPrefsUtil(private val context: Context) {
    private val sharedPreferences = context.getSharedPreferences("FILTER_PREFS", Context.MODE_PRIVATE)
    private val gson = Gson()

    companion object {
        private const val FILTER_KEY = "FILTER"
    }

    // Сохраняем текущий фильтр
    fun saveFilter(filter: Filter) {
        val jsonString = gson.toJson(filter)
        sharedPreferences.edit().putString(FILTER_KEY, jsonString).apply()
    }

    // Получаем текущий фильтр
    fun getFilter(): Filter {
        val jsonString = sharedPreferences.getString(FILTER_KEY, null)
        return if (jsonString != null) {
            gson.fromJson(jsonString, Filter::class.java)
        } else {
            Filter(areaCountry = null, areaCity = null, industrySP = null, salary = null, withSalary = null)
        }
    }

    // Очищаем все данные
    fun clearAll() {
        sharedPreferences.edit().clear().apply()
    }
}

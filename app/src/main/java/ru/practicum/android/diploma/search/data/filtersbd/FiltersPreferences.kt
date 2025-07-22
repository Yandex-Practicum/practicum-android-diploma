package ru.practicum.android.diploma.search.data.filtersbd

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.search.domain.model.Industry

class FiltersPreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    private val gson = Gson()

    fun saveIndustry(industry: Industry?) {
        prefs.edit {
            putString(KEY_INDUSTRY, industry?.let { gson.toJson(it) })
        }
    }

    fun getIndustry(): Industry? {
        return prefs.getString(KEY_INDUSTRY, null)?.let {
            gson.fromJson(it, Industry::class.java)
        }
    }

    fun saveSalary(salary: String?) {
        prefs.edit {
            putString(KEY_SALARY, salary)
        }
    }

    fun getSalary(): String? {
        return prefs.getString(KEY_SALARY, null)
    }

    fun saveOnlyWithSalary(checked: Boolean) {
        prefs.edit {
            putBoolean(KEY_ONLY_WITH_SALARY, checked)
        }
    }

    fun getOnlyWithSalary(): Boolean {
        return prefs.getBoolean(KEY_ONLY_WITH_SALARY, false)
    }

    fun clearFilters() {
        prefs.edit {
            remove(KEY_INDUSTRY)
            remove(KEY_SALARY)
            remove(KEY_ONLY_WITH_SALARY)
        }
    }

    companion object {
        private const val PREFS_NAME = "filters_prefs"
        private const val KEY_INDUSTRY = "industry"
        private const val KEY_SALARY = "salary"
        private const val KEY_ONLY_WITH_SALARY = "only_with_salary"
    }
}

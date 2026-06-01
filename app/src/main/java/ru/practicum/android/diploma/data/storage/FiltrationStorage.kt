package ru.practicum.android.diploma.data.storage

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.models.FilterParameters

class FiltrationStorage(
    private val sharedPreferences: SharedPreferences,
    private val gson: Gson,
) {

    fun saveFilter(filterParameters: FilterParameters) {
        sharedPreferences.edit {
            putString(FILTER_PARAMETERS_KEY, gson.toJson(filterParameters))
        }
    }

    fun getFilter(): FilterParameters {
        val json = sharedPreferences.getString(FILTER_PARAMETERS_KEY, null) ?: return FilterParameters()
        return runCatching {
            gson.fromJson(json, FilterParameters::class.java)
        }.getOrNull() ?: FilterParameters()
    }

    fun clearFilter() {
        sharedPreferences.edit {
            remove(FILTER_PARAMETERS_KEY)
        }
    }

    companion object {
        const val PREFS_NAME = "filtration_prefs"
        private const val FILTER_PARAMETERS_KEY = "filter_parameters"
    }
}

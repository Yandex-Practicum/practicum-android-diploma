package ru.practicum.android.diploma.data.filters.storage.impl

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.data.filters.storage.api.FilterParametersStorage
import ru.practicum.android.diploma.data.models.storage.FilterParametersDto

class FilterParametersStorageImpl(
    private val gson: Gson,
    private val sharedPreferences: SharedPreferences
) : FilterParametersStorage {

    override fun putFilterParameters(params: FilterParametersDto) {
        sharedPreferences.edit {
            putString(FILTER_PARAMETERS_KEY, gson.toJson(params)).apply()
        }
    }

    override fun getFilterParameters(): FilterParametersDto {
        val json = sharedPreferences.getString(FILTER_PARAMETERS_KEY, null)
        return if (json != null) {
            gson.fromJson(json, FilterParametersDto::class.java)
        } else {
            FilterParametersDto()
        }
    }

    override fun removeFilterParameters() {
        sharedPreferences.edit {
            remove(FILTER_PARAMETERS_KEY).apply()
        }
    }

    companion object {
        private const val FILTER_PARAMETERS_KEY = "key_filter_parameters"
    }
}

package ru.practicum.android.diploma.data.filter

import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.practicum.android.diploma.di.FILTER_KEY
import ru.practicum.android.diploma.domain.models.FilterParameters

class StorageFilter(
    private val sharedPrefs: SharedPreferences,
    private val json: Gson
) {

    fun saveToStorage(filterParameters: FilterParameters) {
        val json = json.toJson(filterParameters)
        sharedPrefs.edit()
            .putString(FILTER_KEY, json)
            .apply()
    }

    fun readFromStorage(): FilterParameters {
        val json: String = sharedPrefs.getString(FILTER_KEY, null) ?: return FilterParameters()
        val itemType = object : TypeToken<FilterParameters>() {}.type

        return Gson().fromJson(json, itemType)
    }

}

package ru.practicum.android.diploma.filter.filter.data.repositoryimpl.sp

import android.content.SharedPreferences
import ru.practicum.android.diploma.filter.filter.domain.repository.FilterSPRepository

class FilterSPRepositoryImpl(private val sharedPreferences: SharedPreferences) : FilterSPRepository {
    override fun putValue(key: String, value: String): Boolean {
        return sharedPreferences.edit().putString(key, value).commit()
    }

    override fun getValue(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    override fun getAll(): Map<String, String> {
        val sharedPrefs = sharedPreferences.all
        val newSP: MutableMap<String, String> = mutableMapOf()
        for ( value in sharedPrefs) {
            newSP[value.key] = value.value.toString()
        }
        return newSP
    }
}

package ru.practicum.android.diploma.data

import android.content.SharedPreferences
import ru.practicum.android.diploma.data.dto.Vacancy
import ru.practicum.android.diploma.domain.api.IStorageRepository
import com.google.gson.Gson

class StorageRepositoryImpl(private val storageSharedPreferences: SharedPreferences, private val gson: Gson) : IStorageRepository {

    override fun read(): List<Vacancy> {
        val json = storageSharedPreferences.getString(STORAGE_PREFERENCES_KEY, null)
            ?: return arrayListOf()
        return ArrayList(gson.Gson().fromJson(json, Array<Vacancy>::class.java).toList())
    }

    override fun write(vacancies: List<Vacancy>) {
        val json = gson.Gson().toJson(vacancies)
        storageSharedPreferences.edit().putString(STORAGE_PREFERENCES_KEY, json).apply()
    }

    companion object {
        const val STORAGE_PREFERENCES = "storage_preferences"
        const val STORAGE_PREFERENCES_KEY = "key_for_storage_preferences"
    }
}

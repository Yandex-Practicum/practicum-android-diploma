package ru.practicum.android.diploma.data

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import ru.practicum.android.diploma.domain.api.IStorageRepository
import ru.practicum.android.diploma.domain.models.SearchVacanciesParam

class StorageRepositoryImpl(
    private val storageSharedPreferences: SharedPreferences,
    private val gson: Gson
) :
    IStorageRepository {

    override fun read(): SearchVacanciesParam {
        val json = storageSharedPreferences.getString(STORAGE_PREFERENCES_KEY, null)
            ?: return SearchVacanciesParam()
        return gson.fromJson(json, SearchVacanciesParam::class.java)
    }

    override fun write(filterParam: SearchVacanciesParam) {
        val json = gson.toJson(filterParam)
        storageSharedPreferences.edit() { putString(STORAGE_PREFERENCES_KEY, json) }
    }

    companion object {
        const val STORAGE_PREFERENCES = "storage_preferences"
        const val STORAGE_PREFERENCES_KEY = "key_for_storage_preferences"
    }
}

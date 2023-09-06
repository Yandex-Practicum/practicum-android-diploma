package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage
) : FilterRepository {

    override fun filter() {}

    override suspend fun getStringFromPrefs(key: String): String {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.STRING)
    }

    override suspend fun saveString(key: String, data: String) {
        sharedPrefsStorage.writeData(key = key, data = data)
    }

    override suspend fun saveCountry(key: String, data: String) {
        sharedPrefsStorage.writeData(key = key, data = data)
    }

}
package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.domain.models.Country
import ru.practicum.android.diploma.filter.domain.models.Region
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage
) : FilterRepository {

    override fun filter() {}

    override suspend fun getCountry(key: String): Country? {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.COUNTRY)
    }

    override suspend fun saveRegion(key: String, data: Region) {
        sharedPrefsStorage.writeData(key = key, data = data)
    }

    override suspend fun saveCountry(key: String, data: Country) {
        sharedPrefsStorage.writeData(key = key, data = data)
    }

}
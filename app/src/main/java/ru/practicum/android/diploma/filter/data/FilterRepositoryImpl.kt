package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.ui.models.SelectedFilter
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage
) : FilterRepository {

    override suspend fun saveSavedFilterSettings(key: String, selectedFilter: SelectedFilter) {
        sharedPrefsStorage.writeData(key = key, data = selectedFilter)
    }

    override suspend fun getSaveFilterSettings(key: String): SelectedFilter {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.OBJECT)
    }

}
package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.data.model.DataType
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import ru.practicum.android.diploma.filter.ui.models.SelectedData
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    private val sharedPrefsStorage: LocalStorage
) : FilterRepository {

    override suspend fun saveSelectedData(key: String, selectedData: SelectedData) {
        sharedPrefsStorage.writeData(key = key, data = selectedData)
    }

    override suspend fun getSelectedData(key: String): SelectedData {
        return sharedPrefsStorage.readData(key = key, defaultValue = DataType.OBJECT)
    }

}
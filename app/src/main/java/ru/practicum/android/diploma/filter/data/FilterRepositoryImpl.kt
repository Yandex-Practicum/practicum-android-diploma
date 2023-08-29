package ru.practicum.android.diploma.filter.data

import ru.practicum.android.diploma.filter.data.local_storage.LocalStorage
import ru.practicum.android.diploma.filter.domain.api.FilterRepository
import javax.inject.Inject

class FilterRepositoryImpl @Inject constructor(
    val sharedPrefsStorage: LocalStorage
) : FilterRepository {
    override fun filter() {

    }
}
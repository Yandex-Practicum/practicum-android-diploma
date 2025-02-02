package ru.practicum.android.diploma.domain.filter.api

import ru.practicum.android.diploma.data.filter.FilterParameters

interface FilterInteractor {
    fun saveToFilterStorage(filterParameters: FilterParameters)

    fun readFromFilterStorage(): FilterParameters
}

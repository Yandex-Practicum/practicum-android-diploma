package ru.practicum.android.diploma.domain.filter.api

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FilterRepository {
    fun saveToFilterStorage(filterParameters: FilterParameters)

    fun readFromFilterStorage(): FilterParameters
}

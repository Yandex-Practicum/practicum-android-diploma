package ru.practicum.android.diploma.domain.filter.api

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FilterInteractor {
    fun saveToFilterStorage(filterParameters: FilterParameters)

    fun readFromFilterStorage(): FilterParameters
}

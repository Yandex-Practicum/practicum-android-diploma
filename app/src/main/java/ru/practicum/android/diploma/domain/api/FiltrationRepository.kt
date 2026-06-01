package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FiltrationRepository {

    fun saveFilter(filterParameters: FilterParameters)

    fun getFilter(): FilterParameters

    fun clearFilter()
}

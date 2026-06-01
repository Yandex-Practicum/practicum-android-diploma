package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.FilterParameters

interface FiltrationInteractor {

    fun saveFilter(filterParameters: FilterParameters)

    fun getFilter(): FilterParameters

    fun clearFilter()

    fun hasActiveFilter(): Boolean
}

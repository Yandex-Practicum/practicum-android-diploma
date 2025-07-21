package ru.practicum.android.diploma.data.filters.storage.api

import ru.practicum.android.diploma.data.models.storage.FilterParametersDto

interface FilterParametersStorage {
    fun putFilterParameters(params: FilterParametersDto)
    fun getFilterParameters(): FilterParametersDto
    fun removeFilterParameters()
}

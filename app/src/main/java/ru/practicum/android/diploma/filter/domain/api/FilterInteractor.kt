package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Filter

interface FilterInteractor {
    fun saveFilter(filter: Filter)
    fun loadFilter(): Filter
}

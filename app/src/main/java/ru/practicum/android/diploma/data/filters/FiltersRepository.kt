package ru.practicum.android.diploma.data.filters

import ru.practicum.android.diploma.domain.models.Filter

interface FiltersRepository {

    fun setFilter(filter: Filter)

    fun getFilters(): Filter

}

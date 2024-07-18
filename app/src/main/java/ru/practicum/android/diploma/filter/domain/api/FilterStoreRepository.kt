package ru.practicum.android.diploma.filter.domain.api

import ru.practicum.android.diploma.filter.domain.models.Filter

interface FilterStoreRepository {
    fun save(filter: Filter)
    fun load(): Filter
}

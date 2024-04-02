package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.ResourceContentSearch

interface CountryRepository {
    fun searchRegion(): Flow<ResourceContentSearch<List<Country>>>
}

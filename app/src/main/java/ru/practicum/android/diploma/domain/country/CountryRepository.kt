package ru.practicum.android.diploma.domain.country

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.util.Resource

interface CountryRepository {
    fun searchRegion(): Flow<Resource<List<Country>>>
}

package ru.practicum.android.diploma.domain.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.util.ResourceContentSearch

interface RegionRepository {
    fun searchRegion(regionId: String): Flow<ResourceContentSearch<Country>>
}

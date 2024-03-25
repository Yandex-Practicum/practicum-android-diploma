package ru.practicum.android.diploma.domain.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.country.Country
import ru.practicum.android.diploma.util.Resource

interface RegionRepository {
    fun searchRegion(regionId: String): Flow<Resource<Country>>
}

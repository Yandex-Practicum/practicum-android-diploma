package ru.practicum.android.diploma.region.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.Resource
import ru.practicum.android.diploma.region.domain.models.RegionItem

interface RegionInteractor {
    fun getRegions(countryId: String?): Flow<Resource<List<RegionItem>>>
}

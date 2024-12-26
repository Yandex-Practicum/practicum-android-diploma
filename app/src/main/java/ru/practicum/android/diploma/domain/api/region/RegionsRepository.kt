package ru.practicum.android.diploma.domain.api.region

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Region
import ru.practicum.android.diploma.util.Resource

interface RegionsRepository {

    fun getRegions(areaId: String? = null): Flow<Resource<List<Region>>>
}

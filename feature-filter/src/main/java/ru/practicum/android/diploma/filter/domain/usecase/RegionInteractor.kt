package ru.practicum.android.diploma.filter.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.filter.domain.model.AreaInReference

interface RegionInteractor {
    fun listAreas(): Flow<Pair<List<AreaInReference>?, String?>>
}

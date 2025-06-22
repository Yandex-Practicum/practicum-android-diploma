package ru.practicum.android.diploma.domain.filters

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Areas

interface AreasInteractor {
    fun getAreas(): Flow<Pair<List<Areas>?, Int?>>
}

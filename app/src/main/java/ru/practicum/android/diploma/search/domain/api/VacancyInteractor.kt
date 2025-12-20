package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.FilterAreaDto

interface VacancyInteractor {
    fun getAreas(): Flow<Pair<List<FilterAreaDto>?, String?>>
}

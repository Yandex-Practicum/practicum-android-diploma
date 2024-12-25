package ru.practicum.android.diploma.data.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.response.VacancySearchResponse
import ru.practicum.android.diploma.domain.search.models.SearchParams

interface VacanciesRepository {
    fun getVacancies(searchParams: SearchParams): Flow<VacancySearchResponse>
}

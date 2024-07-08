package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.data.dto.SearchRequest
import ru.practicum.android.diploma.search.domain.models.VacanciesResponse

interface SearchRepository {
    fun search(options: SearchRequest): Flow<VacanciesResponse>
}

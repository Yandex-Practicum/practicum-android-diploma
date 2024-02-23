package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchResponse

interface SearchRepository {
    fun makeRequest(request: VacanciesSearchRequest): Flow<VacanciesSearchResponse>
}

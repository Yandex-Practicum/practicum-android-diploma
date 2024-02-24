package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.domain.models.main.Vacancy

interface SearchRepository {
    fun makeRequest(request: VacanciesSearchRequest): Flow<Resource<List<Vacancy>>>

    suspend fun getVacancyByQuery(query: String): List<Vacancy>
}

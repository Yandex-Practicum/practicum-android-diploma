package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancylist.dto.VacanciesSearchRequest
import ru.practicum.android.diploma.domain.models.main.Vacancy

interface SearchRepository {
    fun makeRequest(queryMap: Map<String, String>): Flow<Resource<List<Vacancy>>>
}

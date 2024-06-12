package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyPage

interface SearchRepository {
    fun searchRequest(options: Map<String, String>): Flow<Result<VacancyPage>>
}

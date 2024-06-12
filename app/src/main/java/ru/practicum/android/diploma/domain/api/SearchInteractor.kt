package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyPage

interface SearchInteractor {
    fun searchVacancies(options: Map<String, String>): Flow<Result<VacancyPage>>
}

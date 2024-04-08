package ru.practicum.android.diploma.domain.api.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacacy.VacancyResponse

interface VacanciesSearchRepository {

    suspend fun getVacancies(query: String, page: Int): Flow<Pair<VacancyResponse?, String?>>
}

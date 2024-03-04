package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.DetailVacancy

interface VacancyInteractor {
    suspend fun getDetailVacancy(id: String): Flow<Resource<DetailVacancy>>
}

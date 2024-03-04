package ru.practicum.android.diploma.domain.search

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.search.network.Resource
import ru.practicum.android.diploma.domain.models.DetailVacancy
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyData

interface SearchRepository {
    suspend fun search(
        expression: String,
        page: Int,
    ): Resource<VacancyData>
    suspend fun getDetailVacancy(id: String): Flow<Resource<DetailVacancy>>
}

package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsInteractor {
   suspend fun getFullVacancyInfoById(id: String): Flow<NetworkResponse<VacancyFullInfo>>
   suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<Vacancy>>>
}
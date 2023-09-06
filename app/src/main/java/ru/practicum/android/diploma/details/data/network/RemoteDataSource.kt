package ru.practicum.android.diploma.details.data.network

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse

interface RemoteDataSource {
    suspend fun getVacancyFullInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>>

    suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<Vacancy>>>
}
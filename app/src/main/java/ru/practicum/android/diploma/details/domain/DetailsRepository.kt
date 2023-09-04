package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.data.model.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsRepository {
    suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Unit>
    suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>>

}
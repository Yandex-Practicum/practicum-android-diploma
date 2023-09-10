package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo
import ru.practicum.android.diploma.filter.domain.models.NetworkResponse
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsRepository {
    suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit>
    suspend fun getFavoriteVacancy(id: String): Flow<Boolean>
    suspend fun getFullVacancyInfo(id: String): Flow<NetworkResponse<VacancyFullInfo>>
    suspend fun getSimilarVacancies(id: String): Flow<NetworkResponse<List<Vacancy>>>

}
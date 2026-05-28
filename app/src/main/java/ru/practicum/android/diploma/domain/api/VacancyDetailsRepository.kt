package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDetailsRepository {

    suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse

    fun getVacancies(): Flow<List<VacancyDetail>>

    fun getVacancyById(id: String): Flow<VacancyDetail>

    suspend fun addVacancyToFavorites(vacancyDetail: VacancyDetail)

    suspend fun deleteVacancyFromFavorites(id: String)
}

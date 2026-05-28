package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.GetVacancyDetailsResponse
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacancyDetailInteractor {
    suspend fun getVacancyDetails(id: String): GetVacancyDetailsResponse

     suspend fun saveVacancyToFavorites(vacancy: Vacancy)

     fun getVacancyFromFavorites(id: String): Flow<Vacancy?>
}

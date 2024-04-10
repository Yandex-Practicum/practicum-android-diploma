package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoriteVacanciesRepository {
    suspend fun insertVacancy(vacancy: VacancyDetails)
    suspend fun deleteVacancy(vacancyId: String)
    suspend fun getAllVacancies(): Flow<VacancyDetails>
    suspend fun getVacancyById(vacancyId: String): VacancyDetails?
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
}

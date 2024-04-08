package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacacy.Vacancy

interface FavoriteVacanciesRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancyId: String)
    suspend fun getAllVacancies(): Flow<Vacancy>
    suspend fun getVacancyById(vacancyId: String): Vacancy?
    suspend fun isVacancyFavorite(vacancyId: String): Boolean
}

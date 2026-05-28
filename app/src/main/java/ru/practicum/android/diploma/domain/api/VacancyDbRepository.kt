package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDbRepository {
    fun getFavoriteVacancies(): Flow<List<VacancyDetail>>

    suspend fun addVacancyToFavorites(vacancy: VacancyDetail)

    suspend fun deleteVacancyFromFavorites(vacancyId: String)

    suspend fun checkVacancyIsFavorite(vacancyId: String): Boolean
}

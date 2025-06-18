package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoriteInteractor {
    suspend fun addToFavorite(vacancy: VacancyDetails)
    suspend fun delFromFavorite(vacancy: VacancyDetails)
    fun getFavorites(): Flow<List<VacancyDetails>>
    fun getFavoriteById(vacId: String): Flow<VacancyDetails?>
}

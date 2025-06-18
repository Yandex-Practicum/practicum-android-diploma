package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoriteInteractor {
    suspend fun addToFavorite(vacancy: VacancyDetail)
    suspend fun delFromFavorite(vacancy: VacancyDetail)
    fun getFavorites(): Flow<List<VacancyDetail>>
    fun getFavoriteById(vacId: String): Flow<VacancyDetail?>
}

package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface FavoritesRepository {
    suspend fun addToFavorites(vacancy: VacancyDetail)
    suspend fun removeFromFavorites(vacancyId: String)
    fun getFavorites(): Flow<List<Vacancy>>
    suspend fun getFavoriteById(vacancyId: String): VacancyDetail?
    fun isFavorite(vacancyId: String): Flow<Boolean>
}

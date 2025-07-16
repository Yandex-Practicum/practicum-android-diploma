package ru.practicum.android.diploma.favorites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

interface FavoriteVacancyRepository {
    suspend fun addToFavorites(vacancy: FavoriteVacancyEntity)
    suspend fun removeFromFavorites(vacancy: FavoriteVacancyEntity)
    fun getAllFavorites(): Flow<List<FavoriteVacancyEntity>>
    suspend fun isFavorite(id: String): Boolean
    suspend fun removeFromFavorites(id: String)
}

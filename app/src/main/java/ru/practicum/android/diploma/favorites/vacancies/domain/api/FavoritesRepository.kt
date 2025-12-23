package ru.practicum.android.diploma.favorites.vacancies.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity

interface FavoritesRepository {
    fun getFavorites(): Flow<List<FavoriteVacancyEntity>>
    suspend fun addToFavorites(entity: FavoriteVacancyEntity)
    suspend fun removeFromFavorites(id: String)
    suspend fun isFavorite(id: String): Boolean
}

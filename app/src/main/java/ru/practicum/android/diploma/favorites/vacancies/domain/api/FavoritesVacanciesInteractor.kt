package ru.practicum.android.diploma.favorites.vacancies.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.search.domain.model.Result as FavoritesResult

interface FavoritesVacanciesInteractor {
    fun getFavorites(): Flow<FavoritesResult<List<FavoriteVacancyEntity>>>
    suspend fun addToFavorites(entity: FavoriteVacancyEntity)
    suspend fun removeFromFavorites(id: String)
    suspend fun isFavorite(id: String): Boolean
    suspend fun getFavoriteById(id: String): FavoriteVacancyEntity?
}

package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.db.VacancyEntity

interface FavoritesRepository {
    suspend fun addToFavorites(vacancy: VacancyEntity)
    suspend fun removeFromFavorites(id: String)
    suspend fun isFavorite(id: String): Boolean
    suspend fun getFavoriteById(id: String): VacancyEntity?
    suspend fun getAllFavorites(): List<VacancyEntity>
}

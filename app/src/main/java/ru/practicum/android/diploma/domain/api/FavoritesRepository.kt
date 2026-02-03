package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesRepository {
    suspend fun addToFavorites(vacancy: Vacancy)
    suspend fun removeFromFavorites(id: String)
    suspend fun isFavorite(id: String): Boolean
    suspend fun getFavoriteById(id: String): Vacancy?
    suspend fun getAllFavorites(): List<Vacancy>
}

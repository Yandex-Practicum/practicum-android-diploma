package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesRepository {
    suspend fun addVacancyToFavorites(vacancy: Vacancy)

    suspend fun getFavoriteVacancies(limit: Int, from: Int): List<Vacancy>

    suspend fun removeVacancyFromFavorites(vacancy: Vacancy)

    suspend fun isVacancyFavorite(vacancy: Vacancy): Boolean
}

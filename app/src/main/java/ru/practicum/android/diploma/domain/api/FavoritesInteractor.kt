package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoritesInteractor {

    suspend fun isFavorite(vacancyId: String): Boolean

    suspend fun toggleFavorite(
        vacancy: Vacancy,
        isFavorite: Boolean
    ): Boolean
}

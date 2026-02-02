package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.data.db.VacancyEntity

interface FavoritesInteractor {

    suspend fun isFavorite(vacancyId: String): Boolean

    suspend fun toggleFavorite(
        vacancy: VacancyEntity,
        isFavorite: Boolean
    ): Boolean
}

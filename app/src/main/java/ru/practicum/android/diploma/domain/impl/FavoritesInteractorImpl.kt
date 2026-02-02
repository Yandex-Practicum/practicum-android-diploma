package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.data.db.VacancyEntity
import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {

    override suspend fun isFavorite(vacancyId: String): Boolean {
        return repository.isFavorite(vacancyId)
    }

    override suspend fun toggleFavorite(
        vacancy: VacancyEntity,
        isFavorite: Boolean
    ): Boolean {
        return if (isFavorite) {
            repository.removeFromFavorites(vacancy.id)
            false
        } else {
            repository.addToFavorites(vacancy)
            true
        }
    }
}

package ru.practicum.android.diploma.domain.impl

import ru.practicum.android.diploma.domain.api.FavoritesInteractor
import ru.practicum.android.diploma.domain.api.FavoritesRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoritesInteractorImpl(
    private val repository: FavoritesRepository
) : FavoritesInteractor {

    override suspend fun isFavorite(vacancyId: String): Boolean {
        return repository.isFavorite(vacancyId)
    }

    override suspend fun toggleFavorite(
        vacancy: Vacancy,
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

    override suspend fun getAllFavorites(): List<Vacancy> {
        return repository.getAllFavorites()
    }
}

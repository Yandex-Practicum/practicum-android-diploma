package ru.practicum.android.diploma.favorites.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.data.db.entity.FavoriteVacancyEntity

class FavoriteVacancyInteractorImpl(
    private val repository: FavoriteVacancyRepository
) : FavoriteVacancyInteractor {

    override suspend fun addToFavorites(vacancy: FavoriteVacancyEntity) {
        repository.addToFavorites(vacancy)
    }

    override suspend fun removeFromFavorites(vacancy: FavoriteVacancyEntity) {
        repository.removeFromFavorites(vacancy)
    }

    override fun getAllFavorites(): Flow<List<FavoriteVacancyEntity>> {
        return repository.getAllFavorites()
    }

    override suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }
}



package ru.practicum.android.diploma.favorites.vacancies.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesRepository

class FavoritesVacanciesInteractorImpl(
    private val repository: FavoritesVacanciesRepository
) : FavoritesVacanciesInteractor {

    override fun getFavorites(): Flow<List<FavoriteVacancyEntity>> {
        return repository.getFavorites()
    }

    override suspend fun addToFavorites(entity: FavoriteVacancyEntity) {
        repository.addToFavorites(entity)
    }

    override suspend fun removeFromFavorites(id: String) {
        repository.removeFromFavorites(id)
    }

    override suspend fun isFavorite(id: String): Boolean {
        return repository.isFavorite(id)
    }
}

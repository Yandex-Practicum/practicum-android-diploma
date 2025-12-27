package ru.practicum.android.diploma.favorites.vacancies.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesInteractor
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesRepository
import ru.practicum.android.diploma.search.data.network.Resource as FavoritesResourse
import ru.practicum.android.diploma.search.domain.model.Result as FavoritesResult

class FavoritesVacanciesInteractorImpl(
    private val repository: FavoritesVacanciesRepository
) : FavoritesVacanciesInteractor {

    override fun getFavorites(): Flow<FavoritesResult<List<FavoriteVacancyEntity>>> =
        repository.getFavorites()
            .map { resource ->
                when (resource) {
                    is FavoritesResourse.Success -> FavoritesResult.Success(resource.data)
                    is FavoritesResourse.Error -> FavoritesResult.Error(
                        message = resource.message,
                        exception = resource.exception
                    )
                }
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

    override suspend fun getFavoriteById(id: String): FavoriteVacancyEntity? {
        return repository.getFavoriteById(id)
    }
}

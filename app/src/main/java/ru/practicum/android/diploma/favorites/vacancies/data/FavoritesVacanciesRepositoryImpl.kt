package ru.practicum.android.diploma.favorites.vacancies.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.favorites.vacancies.data.db.dao.FavoriteVacancyDao
import ru.practicum.android.diploma.favorites.vacancies.data.db.entity.FavoriteVacancyEntity
import ru.practicum.android.diploma.favorites.vacancies.domain.api.FavoritesVacanciesRepository
import ru.practicum.android.diploma.search.data.network.Resource as FavoritesResourse

class FavoritesVacanciesRepositoryImpl(
    private val dao: FavoriteVacancyDao
) : FavoritesVacanciesRepository {

    override fun getFavorites(): Flow<FavoritesResourse<List<FavoriteVacancyEntity>>> =
        dao.getAllFavorites()
            .map<List<FavoriteVacancyEntity>, FavoritesResourse<List<FavoriteVacancyEntity>>> { list ->
                FavoritesResourse.Success(list)
            }
            .catch { throwable ->
                val exception = (throwable as? Exception) ?: Exception(throwable)
                emit(
                    FavoritesResourse.Error(
                        message = "Database error while loading favorites",
                        exception = exception
                    )
                )
            }

    override suspend fun addToFavorites(entity: FavoriteVacancyEntity) {
        dao.insertFavorite(entity)
    }

    override suspend fun removeFromFavorites(id: String) {
        dao.deleteFavoriteById(id)
    }

    override suspend fun isFavorite(id: String): Boolean =
        dao.isFavorite(id)

    override suspend fun getFavoriteById(id: String): FavoriteVacancyEntity? {
        return dao.getFavoriteById(id)
    }
}

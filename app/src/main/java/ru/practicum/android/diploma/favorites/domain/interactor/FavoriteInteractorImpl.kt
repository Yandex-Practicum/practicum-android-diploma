package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.favorites.domain.entity.VacancyFavorite
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun getFavoritesList(): Flow<Pair<List<VacancyItems>?, String?>> {
        return favoriteRepository.getVacancyList().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
    override suspend fun isVacancyFavorite(vacancyId: String): Boolean {
        return favoriteRepository.isVacancyFavorite(vacancyId)
    }

    override suspend fun insertFavoriteVacancy(vacancy: VacancyFavorite) {
        favoriteRepository.insertFavoriteVacancy(vacancy)
    }

    override suspend fun deleteVacancyById(id: String) {
        favoriteRepository.deleteVacancyById(id)
    }

    override fun getVacancyById(id: String): Flow<VacancyFavorite?> =
        favoriteRepository.getVacancyById(id)

}

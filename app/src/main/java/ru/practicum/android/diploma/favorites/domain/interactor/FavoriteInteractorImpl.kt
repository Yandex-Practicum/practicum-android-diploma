package ru.practicum.android.diploma.favorites.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.common.domain.models.Resource
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.search.domain.model.VacancyItems

class FavoriteInteractorImpl(
    favoriteRepository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun getFavoritesList(): Flow<Pair<List<VacancyItems>?, String?>> {
        return favoriteRepository.getVacancyList().map {result ->
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
}

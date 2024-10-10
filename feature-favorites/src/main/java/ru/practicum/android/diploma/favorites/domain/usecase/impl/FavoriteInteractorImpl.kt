package ru.practicum.android.diploma.favorites.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.practicum.android.diploma.commonutils.Resource
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.favorites.domain.usecase.FavoriteInteractor

internal class FavoriteInteractorImpl(
    private val favoriteRepository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun getVacancies(): Flow<List<FavoriteVacancy>> {
        return favoriteRepository.getVacancies()
    }

    override suspend fun getVacanciesNumber(): Flow<Pair<Int?, String?>> {
        return favoriteRepository.getVacanciesNumber().map { resource -> Resource.handleResource(resource) }
    }

    override suspend fun getVacanciesPaginated(
        limit: Int,
        offset: Int
    ): Flow<Pair<List<FavoriteVacancy>?, String?>> {
        return favoriteRepository.getVacanciesPaginated(
            limit,
            offset
        ).map { resource ->
            Resource.handleResource(resource)
        }
    }
}

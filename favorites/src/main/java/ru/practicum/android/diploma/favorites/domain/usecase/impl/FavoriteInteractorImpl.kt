package ru.practicum.android.diploma.favorites.domain.usecase.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favorites.domain.model.FavoriteVacancy
import ru.practicum.android.diploma.favorites.domain.repository.FavoriteRepository
import ru.practicum.android.diploma.favorites.domain.usecase.FavoriteInteractor

class FavoriteInteractorImpl (
    private val favoriteRepository: FavoriteRepository
) : FavoriteInteractor {
    override suspend fun getVacancies(): Flow<List<FavoriteVacancy>> {
        return favoriteRepository.getVacancies()
    }
}

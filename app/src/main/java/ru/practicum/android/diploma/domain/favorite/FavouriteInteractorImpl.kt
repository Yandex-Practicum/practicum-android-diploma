package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

class FavouriteInteractorImpl(val favouriteRepository: FavouriteRepository) : FavouriteInteractor {
    override suspend fun addToFavourite(fullVacancy: FullVacancy) {
        return favouriteRepository.addToFavourite(fullVacancy)

    }

    override fun getFavoriteList(): Flow<List<Vacancy>> {
        return favouriteRepository.getFavouriteVacancies()
    }


}
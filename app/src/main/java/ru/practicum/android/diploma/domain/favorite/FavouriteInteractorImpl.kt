package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

class FavouriteInteractorImpl(val favouriteRepository: FavouriteRepository) : FavouriteInteractor {
    override suspend fun addToFavourite(fullVacancy: FullVacancy) {
        return favouriteRepository.addToFavourite(fullVacancy)
    }

    override suspend fun deleteVacancy(vacancy: FullVacancy) {
        favouriteRepository.deleteFromFavourite(vacancy)
    }

    override fun getFavoriteStatus(vacancyId: String): Flow<Boolean> {
        return favouriteRepository.getFavouriteStatus(vacancyId)
    }

    override fun getFavoriteList(): Flow<List<Vacancy>> {
        return favouriteRepository.getFavouriteVacancies()
    }


}
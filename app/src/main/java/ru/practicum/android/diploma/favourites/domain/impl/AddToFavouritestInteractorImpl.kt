package ru.practicum.android.diploma.favourites.domain.impl

import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.AddToFavouritesRepository

class AddToFavouritestInteractorImpl(private val addToFavouritesRepository: AddToFavouritesRepository): AddToFavouritesInteractor{
    override suspend fun checkVacancyInFavourites(vacancyId: Long): Boolean {
        return addToFavouritesRepository.checkVacancyInFavourites(vacancyId)
    }

    override suspend fun addToFavourites(vacancy: DetailVacancy) {
        addToFavouritesRepository.addToFavourites(vacancy)
    }

    override suspend fun removeFromFavourites(vacancy: DetailVacancy) {
        addToFavouritesRepository.removeFromFavourites(vacancy)
    }
}

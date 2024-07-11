package ru.practicum.android.diploma.favourites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.favourites.domain.api.FavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.FavouritesRepository
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

class FavouritesInteractorImpl(private val favouritesRepository: FavouritesRepository) : FavouritesInteractor {

    override fun favouriteVacancies(): Flow<List<Vacancy>> {
        return favouritesRepository.favouriteVacancies()
    }

    override fun favouriteIds(): Flow<List<Int>> {
        return favouritesRepository.favouriteIds()
    }

    override suspend fun changeFavourite(vacancyFull: VacancyFull) {
        if (favouritesRepository.getById(vacancyFull.id) == null) {
            favouritesRepository.upsertVacancy(vacancyFull)
        } else {
            favouritesRepository.deleteVacancy(vacancyFull.id)
        }
    }

}

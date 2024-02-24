package ru.practicum.android.diploma.favourites.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesInteractor
import ru.practicum.android.diploma.favourites.domain.api.GetFavouritesRepository

class GetFavourtiesInteractorImpl(private val repository: GetFavouritesRepository) : GetFavouritesInteractor {
    override suspend fun getFavouritesList(): Flow<List<DetailVacancy>> {
        return repository.getFavouritesList()
    }

    override suspend fun fillVacList(vac: DetailVacancy) {
        repository.fillVacList(vac)
    }
}

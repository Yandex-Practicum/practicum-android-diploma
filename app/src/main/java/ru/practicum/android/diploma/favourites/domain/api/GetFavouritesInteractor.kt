package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy
import ru.practicum.android.diploma.core.domain.model.ShortVacancy
import ru.practicum.android.diploma.favourites.data.entity.FavoriteEntity

interface GetFavouritesInteractor {
    suspend fun getFavouritesList() : Flow<List<DetailVacancy>>

    suspend fun fillVacList(vac: DetailVacancy)
}

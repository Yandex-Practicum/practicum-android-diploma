package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.core.domain.model.DetailVacancy

interface GetFavouritesRepository {

    suspend fun getFavouritesList(): Flow<List<DetailVacancy>>

    suspend fun fillVacList(vac: DetailVacancy)
}

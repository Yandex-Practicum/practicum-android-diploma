package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface FavoritesInteractor {
   suspend fun getFavorites(): Flow<List<Vacancy>>
//   suspend fun removeVacancy(id: Int)
}
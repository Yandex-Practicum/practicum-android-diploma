package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy

interface FavoritesInteractor {
   suspend fun getFavorites(): Flow<List<Vacancy>>
//   suspend fun removeVacancy(id: Int)
}
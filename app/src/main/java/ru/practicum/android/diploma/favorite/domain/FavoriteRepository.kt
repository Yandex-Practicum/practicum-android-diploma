package ru.practicum.android.diploma.favorite.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy

interface FavoriteRepository {
   suspend fun getFavsVacancies(): Flow<List<Vacancy>>
}
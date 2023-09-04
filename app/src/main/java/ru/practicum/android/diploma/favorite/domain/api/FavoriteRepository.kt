package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface FavoriteRepository {
   suspend fun getFavsVacancies(): Flow<List<Vacancy>>
   suspend fun removeVacancy(id: String): Flow<Int>
}
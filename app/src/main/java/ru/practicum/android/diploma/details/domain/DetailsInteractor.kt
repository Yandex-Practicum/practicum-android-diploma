package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.Vacancy

interface DetailsInteractor {
   suspend fun addVacancyToFavorites(vacancy: Vacancy): Flow<Long>
   suspend fun removeVacancyFromFavorite(id: Long): Flow<Int>
}
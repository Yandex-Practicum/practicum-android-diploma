package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.FetchResult
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsInteractor {
   suspend fun addVacancyToFavorites(vacancy: Vacancy): Flow<Long>
   suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
   suspend fun getFullVacancyInfo(id: String): Flow<FetchResult>
}
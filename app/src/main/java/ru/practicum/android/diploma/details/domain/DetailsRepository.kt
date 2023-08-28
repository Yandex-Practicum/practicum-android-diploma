package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface DetailsRepository {
    suspend fun removeVacancyFromFavorite(id: Long): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Long>
}
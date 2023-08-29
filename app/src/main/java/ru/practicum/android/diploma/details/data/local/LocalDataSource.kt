package ru.practicum.android.diploma.details.data.local

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy

interface LocalDataSource {
    suspend fun removeVacancyFromFavorite(id: Long): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: Vacancy): Flow<Long>

}
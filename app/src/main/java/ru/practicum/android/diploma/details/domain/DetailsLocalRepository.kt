package ru.practicum.android.diploma.details.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo

interface DetailsLocalRepository {
    suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit>
    suspend fun showIfInFavourite(id: String): Flow<Boolean>
    suspend fun getFavoritesById(id: String): Flow<VacancyFullInfo>
}
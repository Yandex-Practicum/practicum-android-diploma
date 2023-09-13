package ru.practicum.android.diploma.details.data.local

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.details.domain.models.VacancyFullInfo

interface LocalDataSource {
    suspend fun removeVacancyFromFavorite(id: String): Flow<Int>
    suspend fun addVacancyToFavorite(vacancy: VacancyFullInfo): Flow<Unit>
    suspend fun showIfInFavouriteById(id: String): Flow<Boolean>
    suspend fun isVacancyInFavs(id: String): Boolean
    suspend fun getFavoritesById(id: String): Flow<VacancyFullInfo>

}
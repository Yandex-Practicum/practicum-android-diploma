package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.FullVacancy


interface FavouriteInteractor {
    suspend fun addToFavourite(fullVacancy: FullVacancy)

    suspend fun deleteVacancy(vacancy: FullVacancy)

    fun getFavoriteList(): Flow<List<ru.practicum.android.diploma.domain.models.Vacancy>>

    fun getFavoriteStatus(vacancyId: String): Flow<Boolean>
}
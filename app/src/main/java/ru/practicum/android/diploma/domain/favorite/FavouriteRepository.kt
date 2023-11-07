package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

interface FavouriteRepository {
    suspend fun addToFavourite(fullVacancy: FullVacancy)
    fun deleteFromFavourite(fullVacancy: FullVacancy)

    fun getFavouriteVacancies(): Flow<List<Vacancy>>
}
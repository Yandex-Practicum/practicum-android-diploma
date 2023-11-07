package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

interface FavouriteRepository {
    fun addToFavourite(fullVacancy: FullVacancy): Flow<Boolean>
    fun deleteFromFavourite(fullVacancy: FullVacancy)

    fun getFavouriteVacancies(): Flow<List<Vacancy>>
}
package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.detail.FullVacancy

interface FavouriteRepository {
    suspend fun addToFavourite(fullVacancy: FullVacancy)
    suspend fun deleteFromFavourite(fullVacancy: FullVacancy)

    fun getFavouriteStatus(vacancyId: String): Flow<Boolean>

    fun getFavouriteVacancies(): Flow<List<Vacancy>>
    suspend fun getFullVacancy(id:String): FullVacancy?


}
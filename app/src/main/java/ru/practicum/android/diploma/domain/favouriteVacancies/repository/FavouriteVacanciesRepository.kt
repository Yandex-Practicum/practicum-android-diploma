package ru.practicum.android.diploma.domain.favouriteVacancies.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

interface FavouriteVacanciesRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    fun getFavouriteVacancies(): Flow<List<Vacancy>>
}

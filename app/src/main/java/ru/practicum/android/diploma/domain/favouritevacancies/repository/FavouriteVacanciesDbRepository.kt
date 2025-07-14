package ru.practicum.android.diploma.domain.favouritevacancies.repository

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.vacancies.Vacancy

interface FavouriteVacanciesDbRepository {
    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    fun getFavouriteVacancies(): Flow<List<Vacancy>>
}

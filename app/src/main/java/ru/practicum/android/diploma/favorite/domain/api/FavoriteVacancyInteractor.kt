package ru.practicum.android.diploma.favorite.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.vacancy.domain.entity.Vacancy

interface FavoriteVacancyInteractor {

    suspend fun insertVacancy(vacancy: Vacancy)

    suspend fun deleteVacancyById(id: String)

    fun getVacancies(): Flow<List<Vacancy>>

    fun getVacancyByID(id: String): Flow<Vacancy>
}

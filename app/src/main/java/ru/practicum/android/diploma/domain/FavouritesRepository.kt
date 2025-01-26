package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun insertVacancy(vacancy: Vacancy)
    suspend fun deleteVacancy(vacancy: Vacancy)
    fun getVacancies(): Flow<List<Vacancy>>
}

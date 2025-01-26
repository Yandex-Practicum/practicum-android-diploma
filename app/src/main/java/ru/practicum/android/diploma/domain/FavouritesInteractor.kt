package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow

interface FavouritesInteractor {

    fun likeVacancy(vacancy: Vacancy)
    fun dislikeVacancy(vacancy: Vacancy)
    suspend fun getFavouritesVacancy(): Flow<List<Vacancy>>
}

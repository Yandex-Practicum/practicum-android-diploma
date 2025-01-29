package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavouritesInteractor {

    suspend fun likeVacancy(vacancy: Vacancy)
    suspend fun dislikeVacancy(vacancy: Vacancy)
    fun getFavouritesVacancy(): Flow<List<Vacancy>>
}

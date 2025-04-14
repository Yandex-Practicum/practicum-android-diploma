package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow

interface IFavVacanciesInteractor {
    fun getFavorite(): Flow<List<Vacancy>>
    fun addToFavorite(vacancy: Vacancy)
    fun deleteFromFavorite(vacancy: Vacancy)
    suspend fun isChecked(vacancy: Vacancy): Boolean
}

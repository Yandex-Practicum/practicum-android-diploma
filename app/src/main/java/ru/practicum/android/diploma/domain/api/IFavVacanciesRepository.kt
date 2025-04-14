package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow

interface IFavVacanciesRepository {
    fun add(vacancy: Vacancy)
    fun delete(vacancy: Vacancy)
    fun getAll(): Flow<List<Vacancy>>
}

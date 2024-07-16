package ru.practicum.android.diploma.favourites.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.models.Vacancy
import ru.practicum.android.diploma.vacancy.domain.models.VacancyFull

interface FavouritesRepository {
    fun favouriteVacancies(): Flow<List<Vacancy>>
    fun favouriteIds(): Flow<List<Int>>
    suspend fun getById(vacancyId: Int): VacancyFull?
    suspend fun upsertVacancy(vacancyFull: VacancyFull)
    suspend fun updateVacancy(vacancyFull: VacancyFull)
    suspend fun deleteVacancy(vacancyId: Int)
}

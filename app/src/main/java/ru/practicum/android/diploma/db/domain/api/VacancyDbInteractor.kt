package ru.practicum.android.diploma.db.domain.api

import ru.practicum.android.diploma.search.domain.models.Vacancy


interface VacancyDbInteractor {
    suspend fun insertVacancy(vacancy: Vacancy)

    suspend fun deleteVacancy(vacancy: Vacancy)

    suspend fun getFavouriteVacancy(): List<Vacancy>
}
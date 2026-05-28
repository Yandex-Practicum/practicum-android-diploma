package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDbInteractor {
    suspend fun addVacancyToFavorites(vacancy: VacancyDetail)
    suspend fun deleteVacancyFromFavorites(vacancyId: String)
    suspend fun checkVacancyIsFavorite(vacancyId: String): Boolean
}

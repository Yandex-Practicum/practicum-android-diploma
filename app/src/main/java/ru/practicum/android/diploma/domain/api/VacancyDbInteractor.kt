package ru.practicum.android.diploma.domain.api

import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDbInteractor {
    fun addVacancyToFavorites(vacancy: VacancyDetail)
    fun deleteVacancyFromFavorites(vacancyId: String)
    fun checkVacancyIsFavorite(vacancyId: String): Boolean
}

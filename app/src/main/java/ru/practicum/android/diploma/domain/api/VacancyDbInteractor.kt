package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetail

interface VacancyDbInteractor {
    fun addVacancyToFavorites(vacancy: VacancyDetail)
    fun deleteVacancyFromFavorites(vacancyId: String)
//    fun observeFavoriteVacancies(): Flow<Vacancy>
//    fun observeFavoriteVacancy(vacancyId: String): Flow<VacancyDetail>
    fun checkVacancyIsFavorite(vacancyId: String): Boolean
}

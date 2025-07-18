package ru.practicum.android.diploma.domain.interactors

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacanciesPreview
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface FavoritesInteractor {

    suspend fun insertVacancy(vacancy: VacancyDetails)
    suspend fun deleteVacancy(id: String)
    fun getAllVacancies(): Flow<List<VacanciesPreview>>
    suspend fun getVacancy(id: String): VacancyDetails
    suspend fun isFavorite(id: String): Boolean

}

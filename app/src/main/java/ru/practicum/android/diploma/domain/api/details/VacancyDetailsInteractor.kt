package ru.practicum.android.diploma.domain.api.details

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacancyDetailsInteractor {
    suspend fun getVacancyDetails(id: String): Flow<VacancyDetails>

    suspend fun makeVacancyFavorite(vacancy: VacancyDetails)
    suspend fun makeVacancyNormal(vacancyId: String)
    suspend fun isVacancyFavorite(vacancyId: String): Boolean

}

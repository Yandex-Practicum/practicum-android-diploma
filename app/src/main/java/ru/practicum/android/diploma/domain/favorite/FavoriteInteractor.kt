package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

interface FavoriteInteractor {
    suspend fun addVacancy(vacancy: VacancyDetail)
    suspend fun deleteVacancy(vacancyId: Int)
    suspend fun getAppIdVacancy(): List<String>
    suspend fun getVacancyId(id: String): VacancyDetail
    fun getListVacancy(): Flow<List<VacancyDetail>>
}

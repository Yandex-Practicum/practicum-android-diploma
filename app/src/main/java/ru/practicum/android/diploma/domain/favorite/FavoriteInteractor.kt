package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

interface FavoriteInteractor {
    suspend fun addVacancy(vacancy: VacancyDetail)
    suspend fun deleteVacancy(vacancyId: Int)
    fun getListVacancy(): Flow<List<VacancyDetail>>
}

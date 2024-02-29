package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse
import ru.practicum.android.diploma.domain.models.detail.VacancyDetail

interface FavoriteRepository {
    suspend fun addVacancy(vacancy: VacancyDetail)
    suspend fun deleteVacancy(vacancyId: Int)
    fun getListVacancy(): Flow<List<VacancyDetail>>
}

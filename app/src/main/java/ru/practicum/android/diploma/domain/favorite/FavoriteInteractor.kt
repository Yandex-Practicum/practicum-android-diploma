package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDtoResponse

interface FavoriteInteractor {
    suspend fun addVacancy(vacancy: VacancyDetailDtoResponse)
    suspend fun deleteVacancy(vacancyId: Int)
    fun getListVacancy(): Flow<List<VacancyDetailDtoResponse>>
}

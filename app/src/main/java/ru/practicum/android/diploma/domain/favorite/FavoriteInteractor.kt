package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto

interface FavoriteInteractor {
    suspend fun addVacancy(vacancy: VacancyDetailDto)
    suspend fun deleteVacancy(vacancyId: Int)
    fun getListVacancy(): Flow<List<VacancyDetailDto>>
}

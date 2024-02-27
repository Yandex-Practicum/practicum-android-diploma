package ru.practicum.android.diploma.domain.favorite

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetail.dto.responseunits.VacancyDetailDto

interface FavoriteRepository {
    suspend fun addVacancy(vacancy: VacancyDetailDto)
    suspend fun deleteTrack(vacancyId: Int)
    fun getListTracks(): Flow<List<VacancyDetailDto>>
}

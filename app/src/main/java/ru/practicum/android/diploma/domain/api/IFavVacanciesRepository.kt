package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.VacancyDetails

interface IFavVacanciesRepository {
    suspend fun add(vacancy: VacancyDetails)
    suspend fun delete(vacancy: VacancyDetails)
    fun getAll(): Flow<List<VacancyDetails>>
}

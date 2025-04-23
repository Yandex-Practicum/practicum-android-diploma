package ru.practicum.android.diploma.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.mapper.VacancyPageResult
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyLong

interface SearchVacancyRepository {
    fun searchVacancy(vacancyName: String, page: Int, perPage: Int): Flow<Resource<VacancyPageResult>>
    fun searchVacancyDetails(vacancyId: String): Flow<Resource<VacancyLong>>
}

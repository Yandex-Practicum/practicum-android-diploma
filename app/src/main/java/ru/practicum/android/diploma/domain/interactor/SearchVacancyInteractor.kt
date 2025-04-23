package ru.practicum.android.diploma.domain.interactor

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.dto.mapper.VacancyPageResult
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.main.VacancyLong

interface SearchVacancyInteractor {
    fun searchVacancy(vacancyName: String, page: Int, perPage: Int): Flow<Resource<VacancyPageResult>>
    fun searchVacancyDetails(vacancyId: String): Flow<Pair<VacancyLong?, String?>>
}

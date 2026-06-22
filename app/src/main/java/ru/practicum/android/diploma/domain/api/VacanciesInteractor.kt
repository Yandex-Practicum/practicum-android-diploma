package ru.practicum.android.diploma.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.ApiResult
import ru.practicum.android.diploma.domain.models.VacanciesSearchResult
import ru.practicum.android.diploma.domain.models.Vacancy

interface VacanciesInteractor {
    fun searchVacancies(query: String, page: Int): Flow<ApiResult<VacanciesSearchResult>>
    fun getVacancyDetails(vacancyId: String): Flow<ApiResult<Vacancy>>
}

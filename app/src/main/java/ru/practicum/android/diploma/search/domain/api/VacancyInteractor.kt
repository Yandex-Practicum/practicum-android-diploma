package ru.practicum.android.diploma.search.domain.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.search.domain.model.FilterArea
import ru.practicum.android.diploma.search.domain.model.FilterIndustry
import ru.practicum.android.diploma.search.domain.model.Result
import ru.practicum.android.diploma.search.domain.model.VacancyDetail
import ru.practicum.android.diploma.search.domain.model.VacancyResponse

interface VacancyInteractor {
    fun getAreas(): Flow<Result<List<FilterArea>>>
    fun getIndustry(): Flow<Result<List<FilterIndustry>>>

    fun getVacancies(
        area: Int? = null,
        industry: Int? = null,
        text: String? = null,
        salary: Int? = null,
        page: Int? = null,
        onlyWithSalary: Boolean? = null
    ): Flow<Result<VacancyResponse>>

    fun getVacancyById(
        id: String
    ): Flow<Result<VacancyDetail>>
}
